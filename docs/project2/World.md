# World.java

**Source file:** `src/World.java`  
**Package:** (none — top-level class)

---

## What is World?

World is the coordinator of the game. It owns the visual grid — a 2D array of strings — and is responsible for placing entities at the start of the game, advancing the simulation one round at a time, and printing the current state to the screen.

It is important to understand what World does *not* do: World does not own the entity data. The full lists of every rock, paper, and scissors — their IDs and positions — live in the static HashMaps inside the `Rock`, `Paper`, and `Scissors` classes. World just holds a 2D array of short strings (`"R"`, `"P"`, `"S"`, or `null` for empty) that is the visual snapshot of the board. The entity classes and the World array must always agree with each other.

---

## Fields

```java
private final int columns;
private final int rows;
private final String[][] map;
private Random rng;
private int entityCount;
```

- **`columns`** and **`rows`** — the dimensions of the grid, set once at construction. The keyword **`final`** means that once these fields are assigned, they can never be reassigned. Using `final` for values that should never change is a good habit: it prevents accidental modification and communicates the intent clearly to anyone reading the code.
- **`map`** — the 2D grid itself. A **2D array** in Java is an array of arrays. `map[row][column]` accesses one cell. The first index is the row (vertical position), the second is the column (horizontal position). Each cell holds `"R"`, `"P"`, `"S"`, or **`null`**. `null` means "no object" — the cell is empty. When Java creates a `new String[rows][columns]` array, every cell starts as `null` automatically, so an empty grid is available immediately after allocation.
- **`rng`** — a `Random` object used to pick random positions during entity placement at startup. "RNG" stands for Random Number Generator.
- **`entityCount`** — how many entities were placed at startup. This is decremented by `removeEntity()` as entities are removed.

All fields are `private`, meaning only methods inside `World` can access them directly. Other classes must use `World`'s public methods to interact with the grid.

---

## Constructor `World(int columns, int rows, int entityCount)`

```java
public World(int columns, int rows, int entityCount) {
    this.columns = columns;
    this.rows = rows;
    this.map = new String[this.rows][this.columns];
    this.entityCount = entityCount;
    this.rng = new Random();
    this.initEntities();
}
```

The constructor stores the dimensions, allocates the grid array (`new String[rows][columns]`), creates the random number generator, and then immediately calls `initEntities()` to populate the board.

**Note on argument order:** The constructor signature lists `columns` first, then `rows`. In `Main.java` at line 109, the call is `new World(rows, columns, entities)` — the values are passed in the opposite order from what their variable names suggest. This means what the user labeled "rows" gets stored in `this.columns` and vice versa. The game still works because the simulation is symmetric in both directions, but the printed output will be transposed relative to what the user typed. This is a minor bug in the calling code.

---

## `initEntities()` — private

```java
private void initEntities() {
    for (int i = 0; i < this.entityCount; i++) {
        boolean cellFound = false;
        int rps = rng.nextInt(3);

        while (!cellFound) {
            int rowRng = rng.nextInt(this.rows);
            int columnRng = rng.nextInt(this.columns);

            if (this.cellisEmpty(rowRng, columnRng)) {
                cellFound = true;
            }

            switch (rps) {
                case 0: this.addEntity("R", rowRng, columnRng); break;
                case 1: this.addEntity("P", rowRng, columnRng); break;
                case 2: this.addEntity("S", rowRng, columnRng); break;
                default: throw new IllegalArgumentException(...);
            }
        }
    }
}
```

This method places `entityCount` entities onto the map at random positions.

For each entity, it first picks a random type: `rng.nextInt(3)` returns 0, 1, or 2 (representing Rock, Paper, Scissors), stored in `rps`.

Then the `while (!cellFound)` loop runs, picking random row/column coordinates until it finds an empty cell. The `cellFound` flag is only set to `true` inside the `if(cellisEmpty(...))` block.

**One thing to be aware of:** the `switch` statement that calls `addEntity()` is placed *outside* the `if(cellisEmpty)` block, at the same indentation level within the `while` loop. This means `addEntity()` is called on *every* iteration of the while loop — not just when the cell is empty. On iterations where the randomly picked cell was already occupied, the entity gets placed there anyway, potentially overwriting what was there. In practice this is unlikely to cause visible problems because the entity count is capped at half the grid size (so most random picks land on empty cells), but it is a subtle bug in the placement logic.

The `default` case in the switch throws an `IllegalArgumentException`. **Throwing an exception** means the program immediately stops the current code path and reports an error. An `IllegalArgumentException` signals that an invalid argument was passed to a method. In this case it would only trigger if `rng.nextInt(3)` somehow returned something other than 0, 1, or 2 — which cannot normally happen — so this line is a defensive safety check.

---

## `addEntity(String entityType, int rowIdx, int columnIdx)`

```java
void addEntity(String entityType, int rowIdx, int columnIdx) {
    entityType = entityType.toUpperCase();
    switch (entityType) {
        case "R":
            Rock rock = new Rock(new Point(rowIdx, columnIdx));
            this.map[rowIdx][columnIdx] = "R";
            break;
        case "P":
            Paper paper = new Paper(new Point(rowIdx, columnIdx));
            this.map[rowIdx][columnIdx] = "P";
            break;
        case "S":
            Scissors scissors = new Scissors(new Point(rowIdx, columnIdx));
            this.map[rowIdx][columnIdx] = "S";
            break;
        default:
            throw new IllegalArgumentException("invalid input for addEntity()");
    }
}
```

This method adds one entity of a given type at a specific cell. `entityType.toUpperCase()` normalizes the input so `"r"` and `"R"` both work.

The switch creates the appropriate entity object and writes the marker string to the map. The act of calling `new Rock(...)` is what registers the rock in Rock's static HashMaps — the constructor handles that. The `this.map[rowIdx][columnIdx] = "R"` line is what updates the visual grid. Both must happen together to keep the entity class data and the World grid in agreement.

---

## `removeEntity(int rowIdx, int columnIdx)`

```java
void removeEntity(int rowIdx, int columnIdx) {
    String entity = this.map[rowIdx][columnIdx];

    if (entity == null) {
        throw new NullPointerException("removeEntity: no entity at that cell");
    }

    switch (entity) {
        case "R": Rock.removeRock(rowIdx, columnIdx);       this.map[rowIdx][columnIdx] = null; break;
        case "P": Paper.removePaper(rowIdx, columnIdx);     this.map[rowIdx][columnIdx] = null; break;
        case "S": Scissors.removeScissors(rowIdx, columnIdx); this.map[rowIdx][columnIdx] = null; break;
        default: throw new IllegalArgumentException("entity must be R, P, or S");
    }

    entityCount--;
}
```

This method removes an entity at the given cell. It first reads what is in the cell. If the cell is `null` (empty), it throws a `NullPointerException` — removing something from an empty cell makes no sense, so the program stops and reports it. Otherwise, it delegates to the appropriate entity class's static remove method (which cleans up that class's HashMaps), sets the cell back to `null`, and decrements the entity count.

The comment in the source is candid: the developer noted this method "seems kind of redundant" because the attack logic in the entity move methods handles removal inline. That is true — `removeEntity()` exists for completeness and is primarily used by `Tests.java`, not by the game loop itself.

---

## `playRound()`

```java
void playRound() {
    Paper.movePaper(this.map, this.rows, this.columns);
    Scissors.moveScissors(this.map, this.rows, this.columns);
    Rock.moveRock(this.map, this.rows, this.columns);
}
```

This method advances the game by one turn. It calls each entity type's move method in a fixed order: Paper first, then Scissors, then Rock.

The order matters. Paper moves first, so it gets to attack any Rocks that have not moved yet this round. Scissors moves second. Rock moves last. This creates a subtle timing effect: early movers get a slight first-strike advantage in each round.

The `this.map` array is passed to each move method so those methods can read and update the grid. The grid is the bridge between World and the entity classes.

---

## `printWorld()`

```java
void printWorld() {
    for (int row = 0; row < this.rows; row++) {
        for (int column = 0; column < this.columns; column++) {
            if (this.map[row][column] == null) {
                System.out.print(". ");
            } else {
                System.out.print(this.map[row][column] + " ");
            }
        }
        System.out.println();
    }
}
```

This prints the current state of the board to the console. The outer loop goes row by row. The inner loop goes cell by cell within each row. Empty cells print as `". "`. Occupied cells print their letter (`"R "`, `"P "`, or `"S "`). After each row is printed, `System.out.println()` moves the cursor to a new line.

---

## `getCell(int row, int col)`

```java
public String getCell(int row, int col) {
    return this.map[row][col];
}
```

Returns the contents of a single cell — `"R"`, `"P"`, `"S"`, or `null`. This is `public` so that `Tests.java` can inspect specific cells without having direct access to the `map` field (which is `private`).

---

## `cellisEmpty(int rowIdx, int columnIdx)`

```java
boolean cellisEmpty(int rowIdx, int columnIdx) {
    return (this.map[rowIdx][columnIdx] == null);
}
```

Returns `true` if the cell at the given position is `null` (empty), `false` otherwise. Used by `initEntities()` to avoid placing two entities in the same cell.

---

## How World Connects to the Rest of the Game

- `Main` creates exactly one `World` object: `new World(rows, columns, entities)`. The constructor immediately populates the board.
- `Main` calls `world0.playRound()` and `world0.printWorld()` repeatedly until a winner is found.
- The `this.map` array is shared by reference with the entity move methods each round. Changes the entity methods make to the array are immediately visible to World.
- `Main` reads the static entity counts (`Rock.rockCount`, `Paper.paperCount`, `Scissors.scissorsCount`) directly — not through World — to detect the game-over condition.

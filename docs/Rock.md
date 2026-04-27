# Rock.java

**Source file:** `src/entities/Rock.java`  
**Package:** `entities`

---

## What is Rock?

Rock represents the entire population of rock entities in the game — not just one rock, but all of them at once. When the game creates `new Rock(somePoint)`, that object registers itself into two shared tables so the game can find it later. Every round, `moveRock()` is called once and it moves every rock on the board in one pass.

Rock beats Scissors: when a rock's random move lands on a cell containing a Scissors, it eliminates that Scissors and takes its spot.

---

## The `static` Keyword — A Crucial Concept

Before looking at the fields, it is important to understand `static`, because it shapes everything in this class.

A regular (instance) field belongs to one specific object. If you create two Rock objects, each has its own copy of that field. A `static` field belongs to the **class itself** — there is only one copy of it, shared by all Rock objects ever created, and it exists even before any Rock object has been created at all.

Imagine a bulletin board hanging on the wall of a room labeled "Rock." Every individual Rock object can walk in and read or update that bulletin board. The bulletin board (`positionById`, `idByPosition`, `rockCount`, `nextRockId`) always has the complete picture of every rock currently alive in the game.

Static methods work the same way: `moveRock()` is `static`, meaning you call it as `Rock.moveRock(...)` on the class itself, not on any particular Rock object. It reaches into those shared tables and moves everyone.

---

## Fields

### Static (class-level) fields

```java
public static HashMap<String, Point> positionById = new HashMap<>();
public static HashMap<Point, String> idByPosition = new HashMap<>();
public static int rockCount = 0;
public static int nextRockId = 0;
```

A **HashMap** is a data structure that stores **key-value pairs**, similar to a dictionary. You give it a key and it hands back the matching value in one step, no matter how many entries it holds.

- **`positionById`** — key: a rock's ID string (like `"rock3"`); value: a `Point` with its current location. This answers "where is rock3 right now?"
- **`idByPosition`** — key: a `Point` location on the grid; value: the ID of the rock at that spot. This answers "what rock is at row 2, column 4?" This reverse-lookup map is what makes removal fast: when a rock is attacked, the game knows the target's row and column, and this map immediately tells it which rock ID to remove.
- **`rockCount`** — the number of rocks currently alive on the board. `Main.java` reads this every round to decide if the game is over.
- **`nextRockId`** — an ever-increasing counter used to generate unique ID strings. It is never reset, so IDs are always unique.

The `HashMap<String, Point>` syntax is called **generics** — it tells Java what types the key and value are. `String` is the key type, `Point` is the value type.

### Instance fields

```java
public Point position;
public String id;
```

- **`position`** — the Point this rock was placed at when it was constructed. Note: after construction, the current position of a rock is tracked in `positionById`, not in this field. This field is set once and not updated.
- **`id`** — this specific rock's unique name, for example `"rock1"`.

---

## Constructor `Rock(Point point)`

```java
public Rock(Point point) {
    this.position = point;
    rockCount++;
    nextRockId++;
    this.id = ("rock" + nextRockId);
    positionById.put(this.id, this.position);
    idByPosition.put(this.position, this.id);
}
```

When `new Rock(somePoint)` is called, these six things happen in order:

1. `this.position = point` — store the starting Point.
2. `rockCount++` — one more rock is alive, so the live counter goes up by one.
3. `nextRockId++` — advance the ID counter. Because this increment happens *before* the ID is assigned, the first Rock ever created gets `"rock1"` (counter goes 0 → 1, then `"rock" + 1`).
4. `this.id = "rock" + nextRockId` — build the unique ID string by appending the counter.
5. `positionById.put(...)` — register in map 1: "rock1 is at this Point."
6. `idByPosition.put(...)` — register in map 2: "this Point is occupied by rock1."

Both maps are always updated together. They are mirror images of each other and must stay in sync.

---

## `moveRock(String[][] map, int rows, int columns)`

This is the most complex method in the class. It runs once per round and moves every rock on the board.

```java
static public void moveRock(String[][] map, int rows, int columns) {
    Random rng = new Random();
    for (String id : new java.util.ArrayList<>(positionById.keySet())) {
        ...
    }
}
```

**Step 1 — Why the ArrayList copy.**
`positionById.keySet()` gives you a live view of all the keys currently in the map. If you iterate directly over that view and the loop body adds or removes entries from the map, Java will throw a **`ConcurrentModificationException`** — an error meaning "you modified a collection while you were iterating it." To avoid this, the code wraps the key set in `new java.util.ArrayList<>(...)` first. This creates a separate snapshot of the keys. The loop iterates the safe snapshot while the HashMap itself can be modified freely.

**Step 2 — Get the current position.**
```java
Point pos = positionById.get(id);
```
For each rock ID, look up where it is right now.

**Step 3 — Build the list of neighboring cells.**
```java
int[][] neighbors = {
    {pos.x-1, pos.y}, {pos.x+1, pos.y},
    {pos.x, pos.y-1}, {pos.x, pos.y+1}
};
```
This creates a 2D array of four candidate positions: up, down, left, right. Each candidate is stored as a two-element `int[]` array: `[row, col]`.

**Step 4 — Filter out out-of-bounds candidates.**
```java
for (int[] n : neighbors) {
    if (n[0] >= 0 && n[0] < rows && n[1] >= 0 && n[1] < columns) {
        valid.add(n);
    }
}
```
A rock at the edge of the grid cannot move off the board. This loop keeps only the candidates where both indices are within the valid range (0 to rows-1, 0 to columns-1).

**Step 5 — Pick one valid neighbor at random.**
```java
int[] target = valid.get(rng.nextInt(valid.size()));
```
`rng.nextInt(valid.size())` generates a random number from 0 up to (but not including) the number of valid neighbors. `valid.get(index)` retrieves the `int[]` at that position.

**Step 6 — Move or attack.**
```java
if (map[targetRow][targetCol] == null) {
    // move into empty cell
} else if (map[targetRow][targetCol].equals("S")) {
    // attack scissors
}
```

If the target cell is `null` (empty), the rock moves there: clear the old cell, mark the new cell `"R"`, update both HashMaps.

If the target cell contains `"S"` (a Scissors), the rock attacks: call `Scissors.removeScissors(targetRow, targetCol)` to wipe that scissors from Scissors' own tracking maps, then move the rock into that cell.

If the target cell contains `"P"` (a Paper), the rock does nothing — it cannot overpower Paper.

Note: the comparison uses `.equals("S")` rather than `== "S"`. For Strings in Java, `==` compares memory addresses, which is unreliable for strings retrieved from an array. `.equals()` compares the actual characters, which is what you want.

---

## `removeRock(int rowIdx, int columnIdx)`

```java
static public void removeRock(int rowIdx, int columnIdx) {
    Point argPoint = new Point(rowIdx, columnIdx);
    String rockId = idByPosition.get(argPoint);
    positionById.remove(rockId);
    idByPosition.remove(argPoint);
    rockCount--;
}
```

This is called by `Paper.movePaper()` when Paper attacks a Rock. It creates a temporary Point from the given coordinates, uses the reverse-lookup map (`idByPosition`) to find the rock's ID, removes the rock from both maps, and decrements the live count.

The temporary Point created here works because of the `equals()` and `hashCode()` overrides in `Point.java` — a freshly created `new Point(rowIdx, columnIdx)` will match the stored Point entry even though it is a different object in memory.

---

## `rockAttack(String[][] map, int targetRow, int targetCol)`

```java
public void rockAttack(String[][] map, int targetRow, int targetCol) {
    Scissors.removeScissors(targetRow, targetCol);
    map[targetRow][targetCol] = null;
}
```

This instance method exists in the source code but is **never actually called** anywhere in the game. The attack logic is handled inline inside `moveRock()` instead. This is sometimes called "dead code" — code that compiles and runs correctly if called, but nothing in the program ever calls it. It is not a problem, just a leftover from the development process.

---

## How Rock Connects to the Rest of the Game

- `World.addEntity("R", row, col)` calls `new Rock(point)` to place and register a rock at startup.
- `World.playRound()` calls `Rock.moveRock(...)` once per round to move all rocks.
- `Paper.movePaper()` calls `Rock.removeRock(row, col)` when Paper eliminates a Rock.
- `Main` reads `Rock.rockCount` each round to count how many entity types are still alive.
- `Tests.java` directly clears `Rock.positionById`, `Rock.idByPosition`, and sets `Rock.rockCount = 0` before each test to reset the global state.

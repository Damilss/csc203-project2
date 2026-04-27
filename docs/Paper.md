# Paper.java

**Source file:** `src/entities/Paper.java`  
**Package:** `entities`

---

## What is Paper?

Paper manages the entire population of paper entities in the game using the same pattern as Rock. If you have not read `Rock.md` yet, start there — it contains the full explanation of `static` fields, HashMaps, the ArrayList copy trick, and the general move/attack logic. This document focuses on how Paper differs from Rock.

Paper beats Rock: when a paper's random move lands on a cell containing a Rock, it eliminates that Rock and takes its spot.

---

## How Paper Differs from Rock

The structure of Paper is almost identical to Rock. The differences are:

| | Rock | Paper |
|---|---|---|
| ID prefix | `"rock"` | `"paper"` |
| Live count field | `rockCount` | `paperCount` |
| ID counter field | `nextRockId` | `nextPaperId` |
| Map marker string | `"R"` | `"P"` |
| Attacks cells containing | `"S"` (Scissors) | `"R"` (Rock) |
| Calls to remove target | `Scissors.removeScissors(...)` | `Rock.removeRock(...)` |

---

## Fields

### Static (class-level)

```java
public static HashMap<String, Point> positionById = new HashMap<>();
public static HashMap<Point, String> idByPosition = new HashMap<>();
public static int nextPaperId = 0;
public static int paperCount = 0;
```

These four fields serve the same purpose as their counterparts in Rock — see `Rock.md` for the full explanation of each. The two HashMaps are mirror images of each other, always updated together.

### Instance

```java
public Point position;
public String id;
```

Each Paper object stores the Point it was placed at when constructed, and its unique ID string (e.g. `"paper2"`).

---

## Constructor `Paper(Point point)`

```java
public Paper(Point point) {
    this.position = point;
    nextPaperId++;
    paperCount++;
    this.id = ("paper" + nextPaperId);
    positionById.put(this.id, this.position);
    idByPosition.put(this.position, this.id);
}
```

The constructor increments `nextPaperId` before assigning `this.id`, so the first Paper created gets `"paper1"`. Both maps are updated in the same constructor call to keep them in sync.

---

## `movePaper(String[][] map, int rows, int columns)`

The logic is identical to `moveRock()` in structure — see `Rock.md` for the detailed step-by-step walkthrough. The key differences here:

- The map marker written to moved cells is `"P"`.
- The attack condition checks for `"R"` (Rock): `map[targetRow][targetCol].equals("R")`.
- When a Rock is attacked, the code calls `Rock.removeRock(targetRow, targetCol)` to remove it from Rock's tracking maps, then moves the Paper into that cell.
- If the target cell contains `"S"` (Scissors), Paper cannot attack it and stays put.

---

## `removePaper(int rowIdx, int columnIdx)`

```java
static public void removePaper(int rowIdx, int columnIdx) {
    Point argPoint = new Point(rowIdx, columnIdx);
    String paperId = idByPosition.get(argPoint);
    positionById.remove(paperId);
    idByPosition.remove(argPoint);
    paperCount--;
}
```

Called by `Scissors.moveScissors()` when Scissors eliminates a Paper. Creates a temporary Point from the coordinates, looks up the paper's ID in `idByPosition`, removes the paper from both maps, and decrements the live count.

---

## `paperAttack(String[][] map, int targetRow, int targetCol)`

```java
public void paperAttack(String[][] map, int targetRow, int targetCol) {
    Rock.removeRock(targetRow, targetCol);
    map[targetRow][targetCol] = null;
}
```

Like `rockAttack()` in Rock.java, this instance method is **never called** anywhere in the game. The attack logic is handled inline inside `movePaper()`. This is dead code — it compiles fine but nothing triggers it.

---

## How Paper Connects to the Rest of the Game

- `World.addEntity("P", row, col)` calls `new Paper(point)` to place and register a paper at startup.
- `World.playRound()` calls `Paper.movePaper(...)` first, before Scissors and Rock. This means Paper gets first-move advantage each round.
- `Scissors.moveScissors()` calls `Paper.removePaper(row, col)` when Scissors eliminates a Paper.
- `Main` reads `Paper.paperCount` each round to count how many entity types are still alive.
- `Tests.java` clears `Paper.positionById`, `Paper.idByPosition`, and sets `Paper.paperCount = 0` before each test.

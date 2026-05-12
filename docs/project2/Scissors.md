# Scissors.java

**Source file:** `src/entities/Scissors.java`  
**Package:** `entities`

---

## What is Scissors?

Scissors manages the entire population of scissors entities in the game using the same pattern as Rock and Paper. If you have not read `Rock.md` yet, start there — it contains the full explanation of `static` fields, HashMaps, the ArrayList copy trick, and the general move/attack logic. This document focuses on how Scissors differs from Rock and Paper, and on two small inconsistencies worth knowing about.

Scissors beats Paper: when a scissors' random move lands on a cell containing a Paper, it eliminates that Paper and takes its spot.

---

## How Scissors Differs from Rock and Paper

| | Rock | Paper | Scissors |
|---|---|---|---|
| ID prefix | `"rock"` | `"paper"` | `"scissor"` (no trailing s) |
| Live count field | `rockCount` | `paperCount` | `scissorsCount` |
| ID counter field | `nextRockId` | `nextPaperId` | `nextScissorsId` |
| Map marker string | `"R"` | `"P"` | `"S"` |
| Attacks cells containing | `"S"` | `"R"` | `"P"` |
| Calls to remove target | `Scissors.removeScissors(...)` | `Rock.removeRock(...)` | `Paper.removePaper(...)` |

---

## Fields

### Static (class-level)

```java
public static HashMap<String, Point> positionById = new HashMap<>();
public static HashMap<Point, String> IdByPosition = new HashMap<>();
public static int scissorsCount = 0;
public static int nextScissorsId = 0;
```

These serve the same purpose as in Rock and Paper. See `Rock.md` for the full explanation.

**Naming inconsistency:** The second HashMap is named `IdByPosition` with a **capital I** in Scissors.java. In Rock and Paper it is `idByPosition` with a lowercase `i`. Java is case-sensitive, so these are two different names — the field works fine either way, but it is inconsistent with the convention used in the other two classes. `Tests.java` correctly references it as `entities.Scissors.IdByPosition` (with capital I) to match the actual field name. If you ever add code that touches this field, make sure to use the capital I or you will get a compile error.

### Instance

```java
public Point position;
public String id;
```

Each Scissors object stores the Point it was placed at and its unique ID string (e.g. `"scissor0"`).

---

## Constructor `Scissors(Point point)`

```java
public Scissors(Point point) {
    this.position = point;
    this.id = ("scissor" + nextScissorsId);
    scissorsCount++;
    nextScissorsId++;
    positionById.put(this.id, this.position);
    IdByPosition.put(this.position, this.id);
}
```

**Constructor ordering difference:** In Rock and Paper, the counter is incremented *before* the ID is assigned, so the first Rock gets `"rock1"` and the first Paper gets `"paper1"`. In Scissors, the ID is assigned *before* the counter is incremented. That means the first Scissors created gets `"scissor0"` (counter is still 0 when the ID is built), and the second gets `"scissor1"`, and so on.

This is a minor inconsistency across the three classes. It does not affect the game's correctness — IDs only need to be unique, and they are — but it is worth knowing if you ever inspect the ID strings directly.

---

## `moveScissors(String[][] map, int rows, int columns)`

The logic is identical to `moveRock()` in structure — see `Rock.md` for the detailed step-by-step walkthrough. The differences:

- The map marker written to moved cells is `"S"`.
- The attack condition checks for `"P"` (Paper): `map[targetRow][targetCol].equals("P")`.
- When a Paper is attacked, the code calls `Paper.removePaper(targetRow, targetCol)` to remove it from Paper's tracking maps, then moves the Scissors into that cell.
- If the target cell contains `"R"` (Rock), Scissors cannot attack it and stays put.
- The second HashMap is referenced as `IdByPosition` (capital I) throughout this method.

---

## `removeScissors(int rowIdx, int columnIdx)`

```java
static public void removeScissors(int rowIdx, int columnIdx) {
    Point argPoint = new Point(rowIdx, columnIdx);
    String scissorsId = IdByPosition.get(argPoint);
    positionById.remove(scissorsId);
    IdByPosition.remove(argPoint);
    scissorsCount--;
}
```

Called by `Rock.moveRock()` when Rock eliminates a Scissors. Creates a temporary Point, looks up the scissors' ID in `IdByPosition` (capital I), removes the scissors from both maps, and decrements the live count.

---

## `scissorAttack(String[][] map, int targetRow, int targetCol)`

```java
public void scissorAttack(String[][] map, int targetRow, int targetCol) {
    Paper.removePaper(targetRow, targetCol);
    map[targetRow][targetCol] = null;
}
```

Like `rockAttack()` and `paperAttack()`, this instance method is **never called** anywhere in the game. The attack logic is handled inline inside `moveScissors()`. It is dead code — present and correct, but unused.

---

## How Scissors Connects to the Rest of the Game

- `World.addEntity("S", row, col)` calls `new Scissors(point)` to place and register a scissors at startup.
- `World.playRound()` calls `Scissors.moveScissors(...)` second, after Paper but before Rock.
- `Rock.moveRock()` calls `Scissors.removeScissors(row, col)` when Rock eliminates a Scissors.
- `Main` reads `Scissors.scissorsCount` each round to count how many entity types are still alive.
- `Tests.java` clears `Scissors.positionById`, `Scissors.IdByPosition` (capital I), and sets `Scissors.scissorsCount = 0` before each test.

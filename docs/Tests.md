# Tests.java

**Source file:** `src/Tests.java`  
**Package:** (none — top-level class)

---

## What is Tests?

Tests is a standalone program — it has its own `main` method — that verifies the major methods in the game work correctly. It is **not** a JUnit test suite (a formal automated testing framework). Instead, it uses the approach described in the course assignment: print the expected output and the actual output side by side, then visually compare them yourself. There is no automatic pass/fail signal; you read the printed output and decide if the test passed.

To run Tests, compile and run `Tests.java` from the `src/` directory. It takes no command-line arguments.

---

## Why Static State Must Be Reset Before Every Test

The entity classes (`Rock`, `Paper`, `Scissors`) use static HashMaps and counter fields that persist for the entire life of the program. If Test A adds a Rock and does not clean up, Test B will find that earlier rock still registered in `Rock.positionById` and `Rock.idByPosition` — producing wrong results.

To prevent this, every test group in `Tests.java` starts with a manual reset block:

```java
entities.Rock.positionById.clear();
entities.Rock.idByPosition.clear();
entities.Paper.positionById.clear();
entities.Paper.idByPosition.clear();
entities.Scissors.positionById.clear();
entities.Scissors.IdByPosition.clear();   // capital I — matches Scissors.java
entities.Rock.rockCount = 0;
entities.Paper.paperCount = 0;
entities.Scissors.scissorsCount = 0;
```

`HashMap.clear()` removes all entries from a map, leaving it empty. The count fields are manually reset to 0. Notice that Scissors' second map is referenced as `entities.Scissors.IdByPosition` with a capital `I` — this matches the actual field name in `Scissors.java`, which differs from the lowercase convention used in Rock and Paper (see `Scissors.md`).

---

## Test-by-Test Walkthrough

### 1. `addEntity()` test

```java
World testWorld = new World(5, 5, 0);
testWorld.addEntity("R", 0, 0);
System.out.println("Actual: map[0][0] = " + testWorld.getCell(0, 0));
```

Creates a 5x5 world with zero initial entities (so the board starts empty), adds a Rock at row 0, column 0, and checks that `getCell(0, 0)` returns `"R"`. The world is created with `0` entities specifically so `initEntities()` places nothing randomly, giving the test a clean, predictable starting board.

**Expected output:** `map[0][0] = R`

---

### 2. `removeEntity()` test

```java
World removeWorld = new World(5, 5, 0);
removeWorld.addEntity("R", 0, 0);
removeWorld.removeEntity(0, 0);
System.out.println("Actual: map[0][0] = " + removeWorld.getCell(0, 0));
```

Adds a Rock at (0, 0), then removes it. Checks that the cell is now `null`.

**Expected output:** `map[0][0] = null`

---

### 3. `printWorld()` test

```java
World printTestWorld = new World(5, 5, 0);
printTestWorld.addEntity("R", 0, 0);
printTestWorld.addEntity("S", 1, 1);
printTestWorld.printWorld();
```

Places a Rock at the top-left and a Scissors one cell diagonally inward, then prints the board. This is a visual test — you read the printed grid and confirm the letters appear in the right spots with dots everywhere else.

**Expected output:** `R` at the top-left corner, `S` at row 1 column 1, all other cells as `.`

---

### 4. `playRound()` test

```java
World roundWorld = new World(5, 5, 0);
roundWorld.addEntity("R", 2, 2);
roundWorld.addEntity("S", 4, 4);
roundWorld.playRound();
roundWorld.printWorld();
```

Places a Rock and Scissors far apart on a 5x5 board, runs one round, and prints the result. Because movement is random, the exact positions after the round vary. This test is visual — you confirm the entities have moved from their starting positions (or stayed if they happened to try to move into each other's territory).

**Expected output:** both entities should have moved (most of the time), or the board may look the same if one was eliminated.

---

### 5. `moveRock()` test

```java
World rockMoveWorld = new World(5, 5, 0);
rockMoveWorld.addEntity("R", 2, 2);
rockMoveWorld.playRound();
rockMoveWorld.printWorld();
```

Places a single Rock at the center of a 5x5 board and runs one round. The Rock should move to one of its four neighbors: (1,2), (3,2), (2,1), or (2,3). The printed board lets you verify visually.

**Expected output:** `R` appears at one of those four positions.

---

### 6. `rockAttack()` test

```java
World rockAttackWorld = new World(3, 3, 0);
rockAttackWorld.addEntity("R", 1, 1);
rockAttackWorld.addEntity("S", 1, 2);
int scissorsBefore = entities.Scissors.scissorsCount;
for (int i = 0; i < 10; i++) {
    rockAttackWorld.playRound();
}
System.out.println("Actual scissors count after: " + entities.Scissors.scissorsCount);
```

Places a Rock and a Scissors adjacent to each other on a small 3x3 board, then runs 10 rounds. The test name says `rockAttack()`, but it is actually testing the inline attack logic inside `moveRock()` — remember that `rockAttack()` itself is never called in the game (see `Rock.md`).

The test runs 10 rounds rather than 1 because movement is random. On a 3x3 board, a Rock and Scissors starting adjacent will almost certainly collide within 10 rounds — but it is not guaranteed on any single round. After 10 rounds, the scissors count should have decreased from its starting value.

**Expected outcome:** `entities.Scissors.scissorsCount` is less than `scissorsBefore` (likely 0).

---

### 7. `movePaper()` test

Same structure as the `moveRock()` test, but with Paper at (2,2) on a 5x5 board. Verifies Paper moves to one of its four neighbors after one round.

---

### 8. `paperAttack()` test

Same structure as the `rockAttack()` test, but with Paper at (1,1) and Rock at (1,2) on a 3x3 grid. Runs 10 rounds and checks that `Rock.rockCount` decreased.

---

### 9. `moveScissors()` test

Same structure as the `moveRock()` test, but with Scissors at (2,2) on a 5x5 board. Verifies Scissors moves to one of its four neighbors after one round.

---

### 10. `scissorAttack()` test

Same structure as the `rockAttack()` test, but with Scissors at (1,1) and Paper at (1,2) on a 3x3 grid. Runs 10 rounds and checks that `Paper.paperCount` decreased.

---

## Limitations of This Testing Approach

**No automatic pass/fail.** Every test prints "Expected" and "Actual" values. You must read the output yourself and decide whether they match. A bug that produces wrong output looks exactly like a passing test in structure — only the numbers differ.

**Random movement makes attack tests non-deterministic.** The attack tests run 10 rounds to increase the probability of a collision, but there is no mathematical guarantee. On very rare runs, a Rock and Scissors sitting adjacent on a 3x3 board could randomly avoid each other for 10 full rounds, and the test would appear to "fail" even though the logic is correct. If an attack test seems to fail, run it again before concluding there is a bug.

**Static state accumulates if resets are incomplete.** The reset blocks at the beginning of each test group must clear all six HashMaps and all three count fields. If any line is missing or the reset is skipped, later tests will see leftover state from earlier ones and produce confusing results.

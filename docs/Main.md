# Main.java

**Source file:** `src/Main.java`  
**Package:** (none — top-level class)

---

## What is Main?

Main is the entry point of the program — it is the class that Java runs first. Its `main` method does three things in sequence: collect the game settings from the user (grid size and entity count), count down and launch the game, then run the game loop until one entity type wins.

Main is the only class in this project that is not inside a package. It imports the entity classes with `import entities.Rock;`, `import entities.Paper;`, and `import entities.Scissors;`.

---

## `INVALID_INTEGER` — A Named Constant

```java
public static final String INVALID_INTEGER = "That is not a valid integer.";
```

This is a **named constant** — a value that is defined in one place and never changes. The keywords do specific things:

- `static` means it belongs to the class(class level), not to any particular object (see `Rock.md` for the full explanation of `static`).
- `final` means once it is assigned, it can never be reassigned (see `World.md` for the full explanation of `final`).
- Together, `static final` on a `String` field is the standard Java way to declare a constant.

Using a constant means the error message is written once. If it ever needs to change, you change it in exactly one place rather than hunting through the code for every place you typed the same string.

---

## `main(String[] args) throws InterruptedException`

The `main` method signature includes `throws InterruptedException`. The **`throws`** keyword means: "this method might produce an error of type `InterruptedException`, and I am choosing not to handle it here — whoever called this method will deal with it." In practice, the JVM itself calls `main`, and if the sleep is interrupted, the program simply stops. For a simple program like this, passing the responsibility up with `throws` is fine.

`InterruptedException` is thrown by `TimeUnit.SECONDS.sleep(1)` — the sleep countdown before the game starts. The operating system can sometimes interrupt a sleeping thread, which produces this exception.

---

## Collecting User Input with Scanner

```java
Scanner scanner = new Scanner(System.in);
```

`Scanner` is a class from `java.util` that reads text from a source. `System.in` is the standard input stream — the keyboard. `scanner.nextLine().trim()` reads one full line of text that the user typed and removes any leading or trailing whitespace.

---

## Input Validation: The `while` + `try/catch` Pattern

The code uses the same pattern three times: once for columns, once for rows, and once for entity count. Here is the columns version:

```java
boolean columnsFlag = false;
while (!columnsFlag) {
    System.out.print("Please enter how many columns: ");
    String input = scanner.nextLine().trim();

    try {
        columns = Integer.parseInt(input);
        columnsFlag = true;
    } catch (NumberFormatException e) {
        System.out.println(INVALID_INTEGER);
    }
}
```

### How `try/catch` works

The **`try` block** contains the code you attempt to run. If that code throws an exception, execution jumps immediately to the **`catch` block**, skipping any remaining lines in the `try`. The `catch` block handles the error — here, it prints the error message and lets the loop continue.

**`NumberFormatException`** is the specific exception that `Integer.parseInt()` throws when the string it receives cannot be converted to a whole number. For example, `Integer.parseInt("hello")` throws this exception, but `Integer.parseInt("5")` succeeds and returns the integer `5`.

The `columnsFlag` variable is only set to `true` inside the `try` block, on the line after the successful `parseInt`. If `parseInt` throws an exception, that `columnsFlag = true` line is never reached, so the while loop runs again and asks for input a second time. This continues until the user types a valid integer.

### Entity count validation

The entity count loop adds one extra check:

```java
if (entities < 0 || entities > (rows * columns) / 2) {
    throw new NumberFormatException(INVALID_INTEGER);
}
```

There are two ways an otherwise-valid integer can still be rejected:

- **Too small (`entities < 0`):** A negative number is meaningless as an entity count. Without this check, entering `-1` would pass, `initEntities()` would receive `-1`, its `for` loop would never execute (since `0 < -1` is immediately false), and the game would start with zero entities and instantly print "Game Over." — confusing but not a crash.
- **Too large (`entities > (rows * columns) / 2`):** More than half the grid would make the `initEntities()` loop in `World` spend a very long time searching for empty cells.

If either condition is true, the code manually **throws** a `NumberFormatException`. Throwing an exception is how you intentionally trigger the `catch` block. This is an unconventional pattern — normally `NumberFormatException` is reserved for non-integer strings — but it works here because all types of invalid input should produce the same error message and the same behavior: loop again.

---

## Countdown Timer

```java
for (int i = 0; i < 3; i++) {
    int timer = 3 - i;
    if (timer == 3) {
        System.out.printf("Starting in %d\n", timer);
    } else {
        System.out.printf("%d\n", timer);
    }
    TimeUnit.SECONDS.sleep(1);
}
scanner.close();
```

This loops three times, printing `"Starting in 3"`, then `"2"`, then `"1"`, with a one-second pause between each. The first iteration gets the special "Starting in" prefix; the rest just print the number.

`TimeUnit.SECONDS.sleep(1)` is cleaner than `Thread.sleep(1000)` because the unit (seconds) is explicit rather than hidden in the number 1000.

`scanner.close()` is called once input collection is finished. This frees the keyboard resource. The source code notes that the IDE suggested a "try-with-resources" pattern for Scanner, but the developers decided the manual close was simpler here.

---

## Creating the World

```java
World world0 = new World(rows, columns, entities);
world0.printWorld();
```

This creates the game world with the dimensions and entity count the user provided, then immediately prints the initial board state. The act of constructing `World` triggers `initEntities()`, which places all entities at random positions.

The `World` constructor signature is `World(int rows, int columns, int entityCount)`, matching the order the arguments are passed here.

---

## The Game Loop

```java
while (true) {
    int typesRemaining = 0;
    if (Rock.rockCount > 0)     typesRemaining++;
    if (Paper.paperCount > 0)   typesRemaining++;
    if (Scissors.scissorsCount > 0) typesRemaining++;

    if (typesRemaining <= 1) {
        System.out.println("Game Over.");
        if (Rock.rockCount > 0)     System.out.println("Rock Wins!");
        if (Paper.paperCount > 0)   System.out.println("Paper Wins!");
        if (Scissors.scissorsCount > 0) System.out.println("Scissors Wins!");
        break;
    }

    world0.playRound();
    world0.printWorld();
}
```

The loop runs indefinitely (`while (true)`) but has an exit condition at the top. Each iteration:

1. Counts how many entity types have at least one member still alive (`typesRemaining`).
2. If one or zero types remain, the game is over: print the winner and **`break`** out of the loop.
3. Otherwise, advance the simulation one round with `playRound()` and print the updated board.

**`break`** immediately exits the nearest enclosing loop. Without it, `while (true)` would run forever.

The game reads the static `rockCount`, `paperCount`, and `scissorsCount` directly from the entity classes — not through `World`. This is possible because those fields are `public static`.

The game ends when `typesRemaining <= 1` rather than `== 0`. This handles both the normal case (one type wins and eliminates the others) and the edge case where all entities somehow eliminate each other simultaneously (count reaches 0).

---

## How Main Connects to the Rest of the Game

Main is the top-level orchestrator. It creates the single `World` object, calls `playRound()` and `printWorld()` each turn, and reads the static entity counts to detect the end condition. It does not directly manipulate entities or the grid — all of that is delegated to `World` and the entity classes.

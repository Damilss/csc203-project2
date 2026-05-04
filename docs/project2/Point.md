# Point.java

**Source file:** `src/entities/Point.java`  
**Package:** `entities`

---

## What is Point?

Point is a simple class that holds the location of one entity on the game grid. Think of it like a street address: it says "row 3, column 5" so the game always knows where a Rock, Paper, or Scissors piece is sitting. Each entity object carries a Point that was set when the entity was first placed on the board.

The comment at the top of the source file is honest about something: the professor mentioned this class is "kind of not needed" and is required mainly as a design exercise. A plain `int[]` array could hold two coordinates just as well. But as you'll see in the `equals()` and `hashCode()` sections below, writing Point as its own class gives it a superpower that an array does not have: it can be used as a reliable **HashMap key**.

---

## The `package` Declaration

The very first line of the file reads:

```java
package entities;
```

A **package** is Java's way of grouping related classes into a folder-level namespace. Because `Point.java` lives inside `src/entities/`, it belongs to the `entities` package. Any class outside that package that wants to use Point must import it with:

```java
import entities.Point;
```

or import the whole package with `import entities.*;`. You will see those import lines at the top of `World.java` and the entity classes.

---

## Fields

```java
public int x;
public int y;
```

- `x` holds the **row index** — the first bracket when you access the grid array (`map[x][...]`).
- `y` holds the **column index** — the second bracket (`map[...][y]`).

Both fields are `public`, which means any other class can read or change them directly, without needing a special "getter" method. This is a deliberate simplicity choice for a small project.

---

## Constructor

```java
public Point(int x, int y) {
    this.x = x;
    this.y = y;
}
```

A **constructor** is the block of code that runs the moment you write `new Point(3, 5)`. It takes the two numbers you pass in and stores them in the object.

The word **`this`** is needed here because the constructor's parameter names (`x` and `y`) are the same as the field names. Without `this`, Java would not know which `x` you mean — the argument coming in, or the field on the object. Writing `this.x` means "the `x` field that belongs to this specific Point object."

---

## `equals(Object obj)` — Comparing Points by Value

```java
@Override
public boolean equals(Object obj) {
    if (this == obj)       { return true; }
    if (!(obj instanceof Point other)) { return false; }
    return this.x == other.x && this.y == other.y;
}
```

### Why this method exists

By default, Java compares two objects by their **memory address** — the location in your computer's RAM where the object is stored. This means two separately created `new Point(3, 5)` objects are considered *different*, even though they store the same row and column numbers, because they live at different memory addresses.

That default behavior would completely break the `idByPosition` HashMap used in `Rock`, `Paper`, and `Scissors`. Here is why: when `moveRock()` wants to look up which rock lives at a certain position, it does something like:

```java
String rockId = idByPosition.get(new Point(targetRow, targetCol));
```

It creates a **brand-new** Point object with the target coordinates and hands it to `get()`. If Java compared Points by memory address, `get()` would never find a match, because this new Point object has a different address than the one that was originally stored in the map — even if the row and column are identical. The lookup would silently return `null` every time, and the game would break.

By overriding `equals()` to compare `x` and `y` values instead, two Points with the same coordinates are considered equal, and the HashMap lookup works correctly.

### What each line does

1. `if (this == obj) return true;` — If someone is comparing a Point to itself (the exact same object in memory), it is trivially equal. This is a fast short-circuit check.
2. `if (!(obj instanceof Point other)) return false;` — The `instanceof` keyword checks whether `obj` is actually a `Point`. If it is not (for example, someone accidentally passes in a String), they cannot possibly be equal. The `Point other` part is a modern Java feature called a **pattern-matching instanceof**: it both checks the type and creates a new variable called `other` already cast to `Point`, all in one step. If the check fails, `other` is never created.
3. `return this.x == other.x && this.y == other.y;` — The actual value comparison. Both coordinates must match for the Points to be equal.

### The `@Override` annotation

`@Override` tells the Java compiler: "I am intentionally replacing (overriding) a method that was defined in a parent class." Every Java class automatically inherits from a class called `Object`, and `Object` provides a default `equals()` method. By writing `@Override`, we are replacing that default with our own. If you misspell the method name or get the parameter type wrong, the compiler will catch it immediately rather than silently creating a second, unrelated method.

---

## `hashCode()` — The Required Partner to `equals()`

```java
@Override
public int hashCode() {
    return Objects.hash(x, y);
}
```

### What a hash code is

A **hash code** is an integer that Java computes from an object. When you call `HashMap.put(key, value)`, Java uses the key's hash code to decide which internal "bucket" (storage slot) the entry goes into. When you later call `HashMap.get(key)`, Java computes the hash code of the key you pass in and goes straight to that bucket. This makes lookups very fast.

### The contract

There is an important rule in Java: **if two objects are equal according to `equals()`, they must produce the same hash code from `hashCode()`**. If you override `equals()` but leave `hashCode()` alone, two Points with the same coordinates would be considered equal by `equals()` but they would hash to different buckets — and the HashMap would still fail to find the entry. Both methods must be overridden together.

### `Objects.hash(x, y)`

`Objects` (with a capital S) is a utility class from `java.util.Objects` that provides helpful static methods for working with objects. This is an important distinction:

- **`Object`** (no s, in `java.lang`) is the superclass that every Java class inherits from. It provides the default `equals()` and `hashCode()` we are replacing.
- **`Objects`** (with s, in `java.util`) is a separate helper class full of static convenience methods. It is not a parent class; you just call its methods directly.

`Objects.hash(x, y)` takes any number of values, combines them into a single, well-distributed integer, and returns it. You do not need to write the combination math yourself.

The `import java.util.Objects;` line at the top of the file is what makes this class available.

---

## How Point Connects to the Rest of the Game

Point is used in two ways across the entity classes (`Rock`, `Paper`, `Scissors`):

1. As the **value** stored in `positionById` — given a rock's ID string, the map returns a `Point` saying where it is.
2. As the **key** used in `idByPosition` — given a `Point` location on the grid, the map returns the ID of whatever entity is there.

The overrides of `equals()` and `hashCode()` are specifically what make role #2 work. Without them, `idByPosition` would always return `null` on lookups, and `removeRock()` / `removePaper()` / `removeScissors()` would silently do nothing — entities would never be removed from the tracking maps even after being eliminated from the board.

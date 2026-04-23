# Notes

### Mon April 20 

Professor stated that when going through, it would be optimal to use `String[]` rather than `Char[]` for our world since we will be able to use the methods that come with String rather than creating for work for when we are using char.

We will do a visual 2 dimensional array separated by lines throught. If there is no rock paper or scissors within the space, we will put whitespace, and we will represent Rock paper and Scissors by there starting Character.

Questions that arise: 
- How will we get the top bound and bottom bounds for the array?

- -what will move first?-

- -how will we generate the random numbers for the movement of the Rock Paper and Scissors?-


### misc. 
- Rock will move first, then paper, then scissors. 

- `java.util.random` will be use for RNG

### Wed April 22, 2026

- Package import fix: `Paper`, `Rock`, `Scissors`, and `Point` are now grouped under `package entities;`.
- Java rule reminder: classes in a named package cannot import classes from the default package, so `import Point;` was invalid.
- `Point.java` was moved from `src/Point.java` to `src/entities/Point.java` to keep package structure consistent.

- ALSO remember, when initializing the map, the last index of the rows and columns are - 1 to account for 0-based indexing. So if a user wanted a row length that would be 7, the highest number index would be 0. 

**Self Note:** When configuring the world.addEntity(); method, Im coming to the realization that this implementation of the entities really isn't needed. You can simply mutate the `String[][] map` that is given rather than having classes for each rock, paper, and scissor. I don't understand why. It makes it a lot more difficult to implement, but an asignment is an assignment. 

Right now i'm figuring out that implementation. But I will come back to it later and finish it.


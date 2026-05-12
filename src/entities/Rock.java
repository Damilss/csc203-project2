/**
 * filename: Rock.java
 * description: rock entity that moves around the world and attacks scissors
 * authors: Roscoe, Emilio
 * date: april 27, 2026
 */

package entities;


public class Rock extends Entity implements Action{


    public static int rockCount = 0;
    public static int nextRockId = 0;
    private static java.util.List<Rock> instances = new java.util.ArrayList<>();
    //constructor - args are Point point (starting point of the rock)
//    creates new rock, assigns it to id, and registers it into hashmaps

    public Rock(Point point){
        super(point, "rock" + nextRockId);
        rockCount++;
        nextRockId++;
        positionById.put(getId(), getPosition());
        idByPosition.put(getPosition(), getId());
        instances.add(this);
    }
    //constructors
    //could use lower memory data type like a byte instead of int

    /*
     instance move: this rock picks a random neighbor and either steps into it
     or attacks a Scissors. Required by Action interface.
    */
    @Override
    public void move(String[][] map, int rows, int columns){
        Point pos = getPosition();

        int[][] neighbors = {
                {pos.x-1, pos.y}, {pos.x+1, pos.y},
                {pos.x, pos.y-1}, {pos.x, pos.y+1}
        };

        java.util.List<int[]> valid = new java.util.ArrayList<>();
        for(int[] n : neighbors){
            if(n[0] >= 0 && n[0] < rows && n[1] >= 0 && n[1] < columns){
                valid.add(n);
            }
        }

        int[] target = valid.get(rng.nextInt(valid.size()));
        int targetRow = target[0];
        int targetCol = target[1];

        if(map[targetRow][targetCol] == null){
            stepInto(map, pos, targetRow, targetCol);
        } else if(map[targetRow][targetCol].equals("S")){
            Scissors.removeScissors(targetRow, targetCol);
            stepInto(map, pos, targetRow, targetCol);
        }
    }

    /* Shared logic for moving this rock from its current cell into a target cell.
    * Updates the visual grid, the static position maps, and the entity's own position. */
    private void stepInto(String[][] map, Point pos, int targetRow, int targetCol){
        map[pos.x][pos.y] = null;
        map[targetRow][targetCol] = "R";
        Point newPos = new Point(targetRow, targetCol);
        positionById.put(getId(), newPos);
        idByPosition.remove(pos);
        idByPosition.put(newPos, getId());
        setPosition(newPos);
    }

    /* World-facing helper: move every rock once. */
    public static void moveRock(String[][] map, int rows, int columns){
        for (Rock r : new java.util.ArrayList<>(instances)){
            if (rockCount == 0) break;
            r.move(map, rows, columns);
        }
    }

    /* Args integer rowIdx, integer columnIdx
    * mutates static variables for Rock
    * returns void
    */
    public static void removeRock(int rowIdx, int columnIdx){
        Point argPoint = new Point(rowIdx, columnIdx);
        String rockId = idByPosition.get(argPoint);
        if (rockId == null) {
            System.out.println("removeRock: no rock registered at (" + rowIdx + ", " + columnIdx + ")");
            return;
        }
        positionById.remove(rockId);
        idByPosition.remove(argPoint);
        instances.removeIf(r -> rockId.equals(r.getId()));
        rockCount--;
    }

    /* Instance attack required by Action interface. */
    @Override
    public void attack(String[][] map, int targetRow, int targetCol){
        Scissors.removeScissors(targetRow, targetCol);
        map[targetRow][targetCol] = null;
    }

    public static void rockAttack(String[][] map, int targetRow, int targetCol){
        Scissors.removeScissors(targetRow, targetCol);
        map[targetRow][targetCol] = null;
    }

}

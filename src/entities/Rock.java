/**
 * filename: Rock.java
 * description: rock entity that moves around the world and attacks scissors
 * authors: Roscoe, Emilio
 * date: april 27, 2026
 */

package entities;

import java.util.HashMap;
import java.util.Random;


public class Rock {

    /*
    * This class represents a Rock entity in the game world
    * It maintains static HashMaps for tracking positions and IDs of all rocks
    */
    public static HashMap<String, Point> positionById = new HashMap<>();
    public static HashMap<Point, String> idByPosition = new HashMap<>();


    public Point position;
    public String id;

    public static int rockCount = 0;
    public static int nextRockId = 0;
    //constructor - args are Point point (starting point of the rock)
//    creates new rock, assigns it to id, and registers it into hashmaps

    public Rock(Point point){
        this.position = point;
        rockCount++;
        nextRockId++;
        this.id = ("rock"+nextRockId);
        positionById.put(this.id, this.position);
        idByPosition.put(this.position, this.id);
    }
    //constructors
    //could use lower memory data type like a byte instead of int

    /*
     moves all rocks to random neighbor cell every round
     attacks scissors if target cell contains one
     takes in String[][] map, int rows and int columns
     returns void
    */
    static public void moveRock(String[][] map, int rows, int columns){
        Random rng = new Random();
        for (String id : new java.util.ArrayList<>(positionById.keySet())) {
            Point pos = positionById.get(id);

            // list of neighbors
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

            // pick a random valid neighbor
            int[] target = valid.get(rng.nextInt(valid.size()));
            int targetRow = target[0];
            int targetCol = target[1];

            // move if empty
            if(map[targetRow][targetCol] == null){
                map[pos.x][pos.y] = null;
                map[targetRow][targetCol] = "R";
                positionById.put(id, new Point(targetRow, targetCol));
                idByPosition.remove(pos);
                idByPosition.put(new Point(targetRow, targetCol), id);
            } else if(map[targetRow][targetCol].equals("S")){
                Scissors.removeScissors(targetRow, targetCol);
                map[pos.x][pos.y] = null;
                map[targetRow][targetCol] = "R";
                positionById.put(id, new Point(targetRow, targetCol));
                idByPosition.remove(pos);
                idByPosition.put(new Point(targetRow, targetCol), id);
            }


        }
    }

    /* Args integer rowIdx, integer columnIdx
    * mutates class paper and static variables
    * returns void
    */
    static public void removeRock(int rowIdx, int columnIdx){
        Point argPoint = new Point(rowIdx, columnIdx);
        String rockId = idByPosition.get(argPoint);
        /* IllegalStateException signals that the program has reached an impossible state —
         * the caller is asking to remove a rock from a cell that has no rock registered.
         * This is different from IllegalArgumentException (bad input) or NullPointerException
         * (unexpected null). Using it here makes the root cause clearer when debugging.
         * Java SE 8 docs: https://docs.oracle.com/javase/8/docs/api/java/lang/IllegalStateException.html
         */
        if (rockId == null) {
            throw new IllegalStateException(
                "removeRock: no rock registered at (" + rowIdx + ", " + columnIdx + ")"
            );
        }
        positionById.remove(rockId);
        idByPosition.remove(argPoint);
        rockCount--;
    }


    /*
     * Args: none
     * Mutates: none
     * Returns: void
    */
    public void rockAttack(String[][] map, int targetRow, int targetCol){
        Scissors.removeScissors(targetRow, targetCol);
        map[targetRow][targetCol] = null;
    }

}

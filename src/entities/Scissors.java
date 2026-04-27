/**
 * filename: Scissors.java
 * description: scissors entity that moves around the world and attacks paper
 * authors: Roscoe, Emilio
 * date: april 27, 2026
 */

package entities;

import java.util.HashMap;
import java.util.Random;

public class Scissors {

    /*
    * This class represents a Scissors entity in the game world
    * It maintains static HashMaps for tracking positions and IDs of all scissors
    */
    public static HashMap<String, Point> positionById = new HashMap<>();
    public static HashMap<Point, String> IdByPosition = new HashMap<>();

    public Point position;
    public String id;

    public static int scissorsCount = 0;
    public static int nextScissorsId = 0;


    //constructors
    public Scissors(Point point){
        this.position = point;
        this.id = ("scissor"+nextScissorsId);
        scissorsCount++;
        nextScissorsId++;
        positionById.put(this.id, this.position);
        IdByPosition.put(this.position, this.id);
    }

    //methods
    //could use lower memory data type like a byte instead of int
    /*
     * Args: none
     * Mutates: static variables
     * Returns: int[] with [row, column] of new position
     */
    static public void moveScissors(String[][] map, int rows, int columns){
        Random rng = new Random();
        for(String id : new java.util.ArrayList<>(positionById.keySet())){
            Point pos = positionById.get(id);

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
                map[pos.x][pos.y] = null;
                map[targetRow][targetCol] = "S";
                positionById.put(id, new Point(targetRow, targetCol));
                IdByPosition.remove(pos);
                IdByPosition.put(new Point(targetRow, targetCol), id);
            } else if(map[targetRow][targetCol].equals("P")){
                Paper.removePaper(targetRow, targetCol);
                map[pos.x][pos.y] = null;
                map[targetRow][targetCol] = "S";
                positionById.put(id, new Point(targetRow, targetCol));
                IdByPosition.remove(pos);
                IdByPosition.put(new Point(targetRow, targetCol), id);
            }
        }
    }

    /* Args integer rowIdx, Integer columndIdx
    * mutates class paper and static variables
    * returns void
    */
    static public void removeScissors(int rowIdx, int columnIdx){
        Point argPoint = new Point (rowIdx, columnIdx);
        String scissorsId = IdByPosition.get(argPoint);
        positionById.remove(scissorsId);
        IdByPosition.remove(argPoint);
        scissorsCount--;
    }

    /*
     * Args: none
     * Mutates: none
     * Returns: void
     */
    public void scissorAttack(String[][] map, int targetRow, int targetCol){
        Paper.removePaper(targetRow, targetCol);
        map[targetRow][targetCol] = null;
    }
}
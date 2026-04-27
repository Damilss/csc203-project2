package entities;

import java.util.HashMap;


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

    //constructor
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
     * Args: none
     * Mutates: static variables
     * Returns: int[] with [row, column] of new position
     */
    static public int[] moveRock(){
        return null;
    }

    /* Args integer rowIdx, integer columnIdx
    * mutates class paper and static variables
    * returns void
    */
    static public void removeRock(int rowIdx, int columnIdx){
        Point argPoint = new Point(rowIdx, columnIdx);
        String rockId = idByPosition.get(argPoint);
        positionById.remove(rockId);
        idByPosition.remove(argPoint);
        rockCount--;
    }


    /*
     * Args: none
     * Mutates: none
     * Returns: void
     */
    public void rockAttack(){}
}
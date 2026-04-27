package entities;

import java.util.HashMap;

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
    static public int[] moveScissors(){
        return null;
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
    public void scissorAttack(){}
}
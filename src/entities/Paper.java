package entities;

import java.util.HashMap;

public class Paper {

    /*
    * This class represents a Paper entity in the game world
    * It maintains static HashMaps for tracking positions and IDs of all papers
    */
    protected static HashMap <String, Point> positionById = new HashMap<>();
    public static HashMap <Point,String> idByPosition = new HashMap<>();

    public Point position;
    public String id;

    public static int nextPaperId = 0;
    public static int paperCount = 0;


    //construtor
    public Paper(Point point){
        this.position = point;
        nextPaperId++;
        paperCount++;
        this.id = ("paper"+nextPaperId);
        positionById.put(this.id, this.position);
        idByPosition.put(this.position, this.id);
    }
    //methods
    //could use lower memory data type like a byte instead of int
    /*
     * Args: none
     * Mutates: static variables
     * Returns: int[] with [row, column] of new position
     */
    static public int[] movePaper(){
        return null;
    }

    /* Args Integer rowIdx, Integer columnIdx
    *  mutates class Paper and static variables
    * returns void
    */
    static public void removePaper(int rowIdx, int columnIdx){
        Point argPoint = new Point(rowIdx, columnIdx);
        String paperId = idByPosition.get(argPoint);
        positionById.remove(paperId);
        idByPosition.remove(argPoint);
        paperCount--;

    }

    /*
     * Args: none
     * Mutates: none
     * Returns: void
     */
    public void paperAttack(){}
}
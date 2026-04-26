package entities;

import java.util.HashMap;

public class Scissors {
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
    static public int[] moveScissors(){
        return
    }

    static public void removeScissors(int rowIdx, int columnIdx){
        Point argPoint = new Point (rowIdx, columnIdx);
        String scissorsId = IdByPosition.get(argPoint);
        positionById.remove(scissorsId);
        IdByPosition.remove(argPoint);
        scissorsCount--;
    }

    public void scissorAttack(){
        
    }
}

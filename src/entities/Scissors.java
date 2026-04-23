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
    }

    //methods
    static public void moveScissors(){}

    public void removeScissors(){
        
        scissorsCount--;
    }

    public void scissorAttack(){
        
    }
}

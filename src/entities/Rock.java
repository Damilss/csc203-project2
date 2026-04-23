package entities;

import java.util.HashMap;


public class Rock {
    HashMap<String, Point> positionById = new HashMap<>();
    HashMap<Point, String> idByPosition = new HashMap<>();


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
    }

    //methods
    static public void moveRock(){}

    public void rockAttack(){}
}

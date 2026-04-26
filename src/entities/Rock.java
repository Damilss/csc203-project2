package entities;

import java.util.HashMap;


public class Rock {
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

    //could use lower memory data type like a byte instead of int
    static public int[] moveRock(){
        return
    }

    static public void removeRock(int rowIdx, int columnIdx){
        Point argPoint = new Point(rowIdx, columnIdx);
        String rockId = idByPosition.get(argPoint);
        positionById.remove(rockId);
        idByPosition.remove(argPoint);
        rockCount--;
    }

    public void rockAttack(){}
}

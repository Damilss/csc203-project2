package entities;

import java.util.HashMap;

public class Paper {
    public static HashMap <String, Point> positionById = new HashMap<>();
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
    static public int[] movePaper(){
        return
    }


    static public void removePaper(int rowIdx, int columnIdx){
        Point argPoint = new Point(rowIdx, columnIdx);
        String paperId = idByPosition.get(new Point(rowIdx, columnIdx));
        positionById.remove(paperId);
        idByPosition.remove(new Point(rowIdx, columnIdx));
        paperCount--;

    }

    public void paperAttack(){}

}

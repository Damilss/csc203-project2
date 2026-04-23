package entities;

import java.util.HashMap;

public class Paper {
    public HashMap <String, Point> positionById = new HashMap<>();
    public HashMap <Point,String> idByPosition = new HashMap<>();

    public Point position;
    public String id;
    public int nextPaperId = 0;
    public int paperCount = 0;
    

    //construtor
    public Paper(Point point){
        this.position = point;
        nextPaperId++;
        paperCount++;
        this.id = ("paper"+nextPaperId);
    }

    //methods
    static public void movePaper(){}

    public void paperAttack(){}

}

package entities;

public class Paper {
    public Point position;
    private int nextPaperID = 0;
    private int paperCount = 0;
    

    //construtor
    Paper(Point point){
        this.position = point; 
    }

    //methods
    static public void movePaper(){}

    public void paperAttack(){}

}

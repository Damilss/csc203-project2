
public class World {
    
    private final int columns; 
    private final int rows;
    private final String[][] map;
    private static int entityCount;

    //constructor
    World(int columns, int rows){
        this.columns = columns;
        this.rows = rows;
        this.map = new String[this.rows][this.columns];
        entityCount++;

        
    }

    

    //methods
    void addEntity(){}

    void removeEntity(int idx1, int idx2, World world){

    }

    void playRound(){}

    void printWorld(){}
}

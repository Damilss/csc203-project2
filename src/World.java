import entities.Paper;
import entities.Rock;
import entities.Scissors;
import java.util.InputMismatchException;
import java.util.Random;

public class World {
    
    private final int columns; 
    private final int rows;
    private final String[][] map;
    private Random rng;
    private int entityCount;

    //constructor
    World(int columns, int rows, int entityCount){
        this.columns = columns;
        this.rows = rows;
        this.map = new String[this.rows][this.columns];
        this.entityCount = entityCount;
        this.initEntities();
    }

    /* Purpose: To initialize entities at a random place within the map.
    takes no arguments and sets mutates the map in order to prepare the game */
    private void initEntities(){

        //no more than one third of space is filled with entities 
        this.entityCount = rng.nextInt((this.columns * this.rows)/2);


        for (int i = 0; i < this.entityCount;i++){
            //CONTINUE HERE v
            //Add entities at random places
            //Making sure they don't overlap.
        }

    }
    
    /* String entityType must be "R", "P", or "S". 
    * adds entity to object World at specified */
    void addEntity(String entityType, int rowIdx, int columnIdx){
        entityType = entityType.toUpperCase();

        //inputMisMatchException
        switch(entityType){
            case "R":
                this.map[rowIdx][columnIdx] = "R";

            case "P":
                this.map[rowIdx][columnIdx] = "P";
                //insert class and point update
            case "S":
                this.map[rowIdx][columnIdx] = "S";
                //insert class and point update
            default: 
                throw new InputMismatchException(
                    "addEntity arg entityType needs to be R, P or S. "
                );
            
        } 

        
        
        

    }

    void removeEntity(int rowIdx, int columnIdx){        
        if (this.map[rowIdx][columnIdx] == null){
           throw new NullPointerException(
            //insert some text here, I will do it
        );
        }
        this.map[rowIdx][columnIdx] = null;
    }
    //not finished
    void playRound(){
        Rock.moveRock();
        Paper.movePaper();
        Scissors.moveScissors();
    }

    void printWorld(){}
}

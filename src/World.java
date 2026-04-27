/**
 * filename: World.java
 * description: the 2d game world that holds all entities and runs the game
 * authors: Roscoe, Emilio
 * date: april 27, 2026
 */

import entities.Paper;
import entities.Point;
import entities.Rock;
import entities.Scissors;
import java.util.Random;

public class World {
    
    private final int columns; 
    private final int rows;
    private final String[][] map;
    private Random rng;
    private int entityCount;

    //constructor - args are columns, rows and entityCount
//    creates the world grid and places entitiies in random order on it
    public World(int columns, int rows, int entityCount){
        this.columns = columns;
        this.rows = rows;
        this.map = new String[this.rows][this.columns];
        this.entityCount = entityCount;
        this.rng = new Random();
        this.initEntities();

    }
    /* Purpose: To initialize entities at a random place within the map.
    takes no arguments and sets mutates the map in order to prepare the game */
    private void initEntities(){

        /* Due to the user input regulation, 
        no more than one half of space is filled with entities */
        //this.entityCount = rng.nextInt(3);
        for (int i = 0; i < this.entityCount;i++){
            boolean cellFound = false;
            int rps = rng.nextInt(3);

            //making sure the random number generated is an empty cell. 
            while (!cellFound){
                int rowRng = rng.nextInt(this.rows);
                int columnRng = rng.nextInt(this.columns);

                if(this.cellisEmpty(rowRng, columnRng)){
                    cellFound = true;
                }

            //don't switch to rule case
                switch(rps){
                //ROCK
                case(0):
                    this.addEntity("R",  rowRng, columnRng);
                break;
                //PAPER
                case(1):
                    this.addEntity("P", rowRng, columnRng);
                break;
                //SCISSORS
                case(2):
                    this.addEntity("S", rowRng, columnRng);
                break;
                   //default case for invalid input
                default:
                    throw new IllegalArgumentException(
                        Main.INVALID_INTEGER + 
                        "Invalid input for addEntity() in World.java ln 44"
                    );
                }
            }
        }

    }
    
    /* String entityType must be "R", "P", or "S". 
    * adds entity to object World at specified */
    void addEntity(String entityType, int rowIdx, int columnIdx){
        entityType = entityType.toUpperCase();
        //
        switch(entityType){
            //ROCK
            case("R"):
                Rock rock = new Rock(new Point(rowIdx, columnIdx));
                this.map[rowIdx][columnIdx] = "R";
                break;
            //PAPER
            case("P"):
                Paper paper = new Paper(new Point(rowIdx, columnIdx));
                this.map[rowIdx][columnIdx] = "P";
                break;
            case("S"):
                Scissors scissors = new Scissors(new Point(rowIdx, columnIdx));
                this.map[rowIdx][columnIdx] = "S";
                break;
            default:
                throw new IllegalArgumentException(
                    "invalid input for addEntity() in World.java ln 75"
                );

        }
    }
    
    
    /* purpose: checks entity in specifiic row and column, then updates it's respective class
    *then updates evrything else. Althought it seems kind of abstract, and since we aren't allowed
    * to use abstract parent classes yet, this method seems kind of redundant, im going to create it but 
    * but probably avoid using it for now
    */
    void removeEntity(int rowIdx, int columnIdx){  
        String entity = this.map[rowIdx][columnIdx];
        
        //this will be good for debugging
        if (entity == null){
           throw new NullPointerException("""
                 removeEntity args rowIdx and columnIdx has no entity in it\n
                 line 106 in World.javaf
                   """);
        }
            
        //insert some text here, I will do it
        //Add switch case to check if, another IDE susggestion, ignore
        switch(entity){
            case ("R"):
                Rock.removeRock(rowIdx, columnIdx);
                this.map[rowIdx][columnIdx] = null;
                break;
            case ("P"):
                Paper.removePaper(rowIdx, columnIdx);
                this.map[rowIdx][columnIdx] = null;
                break;

            case ("S"):
                Scissors.removeScissors(rowIdx, columnIdx);
                this.map[rowIdx][columnIdx] = null;
               break; 
            default: 
               throw new IllegalArgumentException(
                "removeEntity arg ln 111 entity must be one of R,P,S"
               );
        }
        
        this.map[rowIdx][columnIdx] = null;
        entityCount--;
    }

    //runs one round of game by moving entities - no args and doesn't return
    void playRound(){
        Paper.movePaper(this.map, this.rows, this.columns);
        Scissors.moveScissors(this.map, this.rows, this.columns);
        Rock.moveRock(this.map, this.rows, this.columns);
    }

//    prints current state of world to screen - letteres represent objects, while '.' represents empty cell
//    no args and void return value
    void printWorld(){
        for (int row = 0; row < this.rows; row++){
            for (int column = 0; column < this.columns; column++){
                if (this.map[row][column] == null){
                    System.out.print(". ");
                } else {
                    System.out.print(this.map[row][column] + " ");
                }
            }
            System.out.println();
        }
    }

//    returns value of a specific cell in the map - args are int row and int col
//    returns letter that represents object, or null if empty
    public String getCell(int row, int col){
        return this.map[row][col];
    }

    /* arg0: int rowIdx
    * arg1: int columnIdx
    * return: boolean
    * 
    * checks if the cell within a certain world is empty or not
    */
    boolean cellisEmpty(int rowIdx, int columnIdx){
        return (this.map[rowIdx][columnIdx] == null);
    }
}

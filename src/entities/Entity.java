package entities;

import java.util.HashMap;
import java.util.Random;

public abstract class Entity{
    /* This class represents a superclass entity
    * for rock, paper, and scissors. 
    *
    *
    */
    private Point position; 
    private final String id;
    protected static int entityCount = 0;
    protected static final Random rng = new Random();

    //public because we can then just use methods that come with HashMap
    public static HashMap <String, Point> positionById = new HashMap<>();
    public static HashMap <Point,String> idByPosition = new HashMap<>();

    //constructor
    protected Entity(Point position, String id){
        this.position = position;
        this.id = id;
        entityCount++;
    }

    //set methods
    public void setPosition(Point position){this.position = position;}
    
    //get methods
    public Point getPosition(){return this.position;}

    public String getId(){return this.id;}

    public int getEntityCount(){return entityCount;}

  

}

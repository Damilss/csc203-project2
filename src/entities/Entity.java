package entities;

public abstract class Entity{
    /* This class represents a superclass entity
    * for rock, paper, and scissors. 
    *
    *
    */
    private Point Position; 
    private final String id;

    //constructor
    protected Entity(Point position, String id){
        this.Position = position;
        this.id = id;
    }

    //set methods
    public void setPosition(Point position){
        this.Position = position;
    }
    
    //get methods
    public Point getPosition(){
        return this.Position;
    }

    public String getId(){
        return this.id;
    }

    //methods
    public abstract void move(Point point);

    public abstract void remove(Point point);

    public abstract void attack(Point point);

}


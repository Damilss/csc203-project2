package entities;

//The class point should be able to hold the positiion of each entity in the world
//In other words, this it the active pointer, similar to a program counter, where this is used to manage the points
//Teacher states that this class is kind of not needed and only required by the assignment for extra practice
public class Point {
    protected int x;
    protected int y;

    protected Point(int x, int y){
        this.x = x; 
        this.y = y;
    }
}

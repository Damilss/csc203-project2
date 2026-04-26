package entities;
import java.util.Objects;

//The class point should be able to hold the positiion of each entity in the world
//In other words, this it the active pointer, similar to a program counter, where this is used to manage the points
//Teacher states that this class is kind of not needed and only required by the assignment for extra practice
public class Point {
    public int x;
    public int y;
    

    public Point(int x, int y){
        this.x = x; 
        this.y = y;
    }


    /* @override API docs latest Java SE 26 -> 
    * https://docs.oracle.com/en/java/javase/26/docs/api/java.base/java/lang/Override.html
    * 
    * @override API docs, Java SE 8 -> 
    * https://docs.oracle.com/javase/8/docs/api/java/lang/Override.html
    *
    * - @Override tells jave method is replacing a method from a superclass or interface.
    * - See documentation for more information
    * 
    * This is so that we can compare points with the same x and y values, even while
    * having different instances of the object
    */
    @Override 
    public boolean equals(Object obj) {
        if (this == obj){
            return true;
        }
        //if not same class of point, short circuit and return false.
        if (!(obj instanceof Point other)){
            return false;
        }
        return this.x == other.x && this.y == other.y;
    }
    
    /* import from java.util.Objects; is a utility class jave give for common object-related 
    * helper methods. It contains useful methods like equals(), hashcode(), 
    * 
    * IMPORTANT DISTINCTION: `Object` is a superclass of every java object, while Objects is 
    * a helper class that has static utility methods for common operations on objects.
    * 
    * Java SE 25 docs for java.util.Objects: 
    * https://docs.oracle.com/en/java/javase/25/docs/api/java.base/java/util/Objects.html
    * 
    * java.lang.Object docs: 
    * https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/lang/Object.html
    * 
    * This function is to prevent from using the default hashCode() that comes from inherited 
    * superclass Object. We want to use our own custom hash based from the actualy values. 
    * This givues us objects based on their values, not their hashCodes based off of their 
    * memory references. This is important for our HashMaps to work properly in this 
    * implementation.
    */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}






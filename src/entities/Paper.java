/**
 * filename: Paper.java
 * description: paper entity that moves around the world and attacks rocks
 * authors: Roscoe, Emilio
 * date: april 27, 2026
 */

package entities;

import java.util.HashMap;
import java.util.Random;

public class Paper {

    /*
    * This class represents a Paper entity in the game world
    * It maintains static HashMaps for tracking positions and IDs of all papers
    */
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
    /*
     * Args: none
     * Mutates: static variables
     * Returns: int[] with [row, column] of new position
     */
    static public void movePaper(String[][] map, int rows, int columns){
        Random rng = new Random();

        for(String id : new java.util.ArrayList<>(positionById.keySet())){
            Point pos = positionById.get(id);

            int[][] neighbors = {
                    {pos.x-1, pos.y}, {pos.x+1, pos.y},
                    {pos.x, pos.y-1}, {pos.x, pos.y+1}
            };

            java.util.List<int[]> valid = new java.util.ArrayList<>();
            for(int[] n : neighbors){
                if(n[0] >= 0 && n[0] < rows && n[1] >= 0 && n[1] < columns){
                    valid.add(n);
                }
            }

            int[] target = valid.get(rng.nextInt(valid.size()));
            int targetRow = target[0];
            int targetCol = target[1];

            if(map[targetRow][targetCol] == null){
                map[pos.x][pos.y] = null;
                map[targetRow][targetCol] = "P";
                positionById.put(id, new Point(targetRow, targetCol));
                idByPosition.remove(pos);
                idByPosition.put(new Point(targetRow, targetCol), id);
            } else if(map[targetRow][targetCol].equals("R")){
                Rock.removeRock(targetRow, targetCol);
                map[pos.x][pos.y] = null;
                map[targetRow][targetCol] = "P";
                positionById.put(id, new Point(targetRow, targetCol));
                idByPosition.remove(pos);
                idByPosition.put(new Point(targetRow, targetCol), id);
            }
        }
    }

    /* Args Integer rowIdx, Integer columnIdx
    *  mutates class Paper and static variables
    * returns void
    */
    static public void removePaper(int rowIdx, int columnIdx){
        Point argPoint = new Point(rowIdx, columnIdx);
        String paperId = idByPosition.get(argPoint);
        positionById.remove(paperId);
        idByPosition.remove(argPoint);
        paperCount--;

    }

    /*
     * Args: none
     * Mutates: none
     * Returns: void
     */
    public void paperAttack(String[][] map, int targetRow, int targetCol){
        Rock.removeRock(targetRow, targetCol);
        map[targetRow][targetCol] = null;
    }
}
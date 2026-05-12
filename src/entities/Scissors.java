/**
 * filename: Scissors.java
 * description: scissors entity that moves around the world and attacks paper
 * authors: Roscoe, Emilio
 * date: april 27, 2026
 */

package entities;

public class Scissors extends Entity{

    /*
    * This class represents a Scissors entity in the game world
    * It maintains static HashMaps for tracking positions and IDs of all scissors
    */


    public static int scissorsCount = 0;
    public static int nextScissorsId = 0;
    private static java.util.List<Scissors> instances = new java.util.ArrayList<>();


    //constructors
    public Scissors(Point point){
        super(point, "scissor" + nextScissorsId);
        scissorsCount++;
        nextScissorsId++;
        positionById.put(getId(), getPosition());
        idByPosition.put(getPosition(), getId());
        instances.add(this);
    }

    //methods
    //could use lower memory data type like a byte instead of int

    /* Instance move required by Entity: this scissors picks a random neighbor
     * and either steps into it or attacks a Paper. */
    @Override
    public void move(String[][] map, int rows, int columns){
        Point pos = getPosition();

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
            stepInto(map, pos, targetRow, targetCol);
        } else if(map[targetRow][targetCol].equals("P")){
            Paper.removePaper(targetRow, targetCol);
            stepInto(map, pos, targetRow, targetCol);
        }
    }

    private void stepInto(String[][] map, Point pos, int targetRow, int targetCol){
        map[pos.x][pos.y] = null;
        map[targetRow][targetCol] = "S";
        Point newPos = new Point(targetRow, targetCol);
        positionById.put(getId(), newPos);
        idByPosition.remove(pos);
        idByPosition.put(newPos, getId());
        setPosition(newPos);
    }

    /* World-facing helper: move every scissors once. */
    public static void moveScissors(String[][] map, int rows, int columns){
        for (Scissors s : new java.util.ArrayList<>(instances)){
            if (scissorsCount == 0) break;
            s.move(map, rows, columns);
        }
    }

    /* Args integer rowIdx, integer columnIdx
    * mutates static variables for Scissors
    * returns void
    */
    public static void removeScissors(int rowIdx, int columnIdx){
        Point argPoint = new Point(rowIdx, columnIdx);
        String scissorsId = idByPosition.get(argPoint);
        if (scissorsId == null) {
            System.out.println("removeScissors: no scissors registered at (" + rowIdx + ", " + columnIdx + ")");
            return;
        }
        positionById.remove(scissorsId);
        idByPosition.remove(argPoint);
        instances.removeIf(s -> scissorsId.equals(s.getId()));
        scissorsCount--;
    }

    /* Instance attack required by Entity. */
    @Override
    public void attack(String[][] map, int targetRow, int targetCol){
        Paper.removePaper(targetRow, targetCol);
        map[targetRow][targetCol] = null;
    }
}
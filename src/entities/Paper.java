/**
 * filename: Paper.java
 * description: paper entity that moves around the world and attacks rocks
 * authors: Roscoe, Emilio
 * date: april 27, 2026
 */

package entities;

public class Paper extends Entity{

    /*
    * This class represents a Paper entity in the game world
    * It maintains static HashMaps for tracking positions and IDs of all papers
    */

    public static int nextPaperId = 0;
    public static int paperCount = 0;
    private static java.util.List<Paper> instances = new java.util.ArrayList<>();


    //construtor
    public Paper(Point point){
        super(point, "paper" + nextPaperId);
        nextPaperId++;
        paperCount++;
        positionById.put(getId(), getPosition());
        idByPosition.put(getPosition(), getId());
        instances.add(this);
    }
    //methods
    //could use lower memory data type like a byte instead of int

    /* Instance move required by Entity: this paper picks a random neighbor
     * and either steps into it or attacks a Rock. */
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
        } else if(map[targetRow][targetCol].equals("R")){
            Rock.removeRock(targetRow, targetCol);
            stepInto(map, pos, targetRow, targetCol);
        }
    }

    private void stepInto(String[][] map, Point pos, int targetRow, int targetCol){
        map[pos.x][pos.y] = null;
        map[targetRow][targetCol] = "P";
        Point newPos = new Point(targetRow, targetCol);
        positionById.put(getId(), newPos);
        idByPosition.remove(pos);
        idByPosition.put(newPos, getId());
        setPosition(newPos);
    }

    /* World-facing helper: move every paper once. */
    public static void movePaper(String[][] map, int rows, int columns){
        for (Paper p : new java.util.ArrayList<>(instances)){
            if (paperCount == 0) break;
            p.move(map, rows, columns);
        }
    }

    /* Args integer rowIdx, integer columnIdx
    * mutates static variables for Paper
    * returns void
    */
    public static void removePaper(int rowIdx, int columnIdx){
        Point argPoint = new Point(rowIdx, columnIdx);
        String paperId = idByPosition.get(argPoint);
        if (paperId == null) {
            System.out.println("removePaper: no paper registered at (" + rowIdx + ", " + columnIdx + ")");
            return;
        }
        positionById.remove(paperId);
        idByPosition.remove(argPoint);
        instances.removeIf(p -> paperId.equals(p.getId()));
        paperCount--;
    }

    /* Instance attack required by Entity. */
    @Override
    public void attack(String[][] map, int targetRow, int targetCol){
        Rock.removeRock(targetRow, targetCol);
        map[targetRow][targetCol] = null;
    }
}
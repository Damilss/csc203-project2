package entities;



public interface Action {
    void move(String[][] map, int row, int column);

    void attack(String[][] map, int row, int column);

    default void remove(int row, int column){
        Point argPoint = new Point(row, column);
        String id = Entity.idByPosition.get(argPoint);
        /* IllegalStateException - see removeRock() in Rock.java for full explanation.
         * Short version: used when the program reaches an impossible state, distinct from
         * bad arguments (IllegalArgumentException) or unexpected nulls (NullPointerException).
         */
        if (id == null) {
            throw new IllegalStateException(
                "removePaper: no paper registered at (" + row + ", " + column + ")"
            );
        }

        if (id.startsWith("paper")) {
            Paper.entityCount--;
        } else if (id.startsWith("rock")) {
            Rock.entityCount--;
        } else if (id.startsWith("scissors")) {
            Scissors.entityCount--;
        }

        Entity.positionById.remove(id);
        Entity.idByPosition.remove(argPoint);
        Entity.entityCount--;
    }
}

import entities.Paper;
import entities.Rock;
import entities.Scissors;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * filename: Main.java
 * description: entry point for the game, handles user input and starts the world
 * authors: Roscoe, Emilio
 * date: april 27, 2026
 */

/* Note: Remmeber to account for zero based indexing, if the user inputs a column length of 7, the index is 0-6
*   - I think I dealt with the package handling, but make sure to double check for your IDE. 
*f
*
*/

public class Main {

    public static final String INVALID_INTEGER = ("That is not a valid integer.");
    public static void main(String[] args) throws InterruptedException {
        /*IDE suggests that we should use try-with scanner, not worth the trouble
        * keep it the way it is and just close at the end of Main.main()
        */
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Rock Paper Scissors");
        System.out.println("How big would you like your map?");

        //retieving rows and columns from user.
        boolean columnsFlag = false;
        boolean rowsFlag = false;
        boolean entityFlag = false;

        //initalizing int variables since scanner pulls as string, wfe need to conver to 4 byte int
        int columns= 0; 
        int rows = 0;
        int entities = 0;

        //exception handling for integer
        while (!columnsFlag) {
            System.out.print("Please enter how many columns: ");
            String input = scanner.nextLine().trim();

            try {
                columns = Integer.parseInt(input);
                if (columns <= 0) {
                    System.out.println("Columns must be a positive integer.");
                } else {
                    columnsFlag = true;
                }
            } catch (NumberFormatException e) {
                System.out.println(INVALID_INTEGER);
            }
        }

        //exception handling for integer
        while(!rowsFlag){
            System.out.print("How many rows?: ");
            String input2 = scanner.nextLine().trim();

            try {
                rows = Integer.parseInt(input2);
                if (rows <= 0) {
                    System.out.println("Rows must be a positive integer.");
                } else {
                    rowsFlag = true;
                }
            } catch (NumberFormatException e) {
                System.out.println(INVALID_INTEGER);
            }
        }

        //exception handdling for number of entities
        while(!entityFlag){
            System.out.println("""
                    Please note that the number of objects cannot be larger than 1/2 of
                    the product of your rows and columns multiplied. Individual objects 
                    for this verision, like Rock, Paper and Scissors are automatically 
                    chosen by the program.\n
                    """);
            System.out.print("How many different objects would you like?: ");
            String input3 = scanner.nextLine().trim();

            try {
                entities = Integer.parseInt(input3);

                /* Throwing exception makes sure that entities within array doesn't exceed one half
                * of availible cells. */
                if (entities < 0 || entities > (rows*columns)/2){
                    throw new NumberFormatException(INVALID_INTEGER);
                }
                entityFlag = true;
            } catch (NumberFormatException e) {
                System.out.println(INVALID_INTEGER);
            }
        }

        /* beginning game! this might cause issues lol so keep eye on it if they
        * are issues  in the future, or you are testing, feel free to comment 
        * this out for testing purposes. 
        */ 
        for (int i= 0;  i < 3;  i++){
            int timer= 3-i;
            if (timer == 3){
                System.out.printf("Starting in %d\n", timer);
            } else {
                System.out.printf("%d\n",timer );
            }
            
            TimeUnit.SECONDS.sleep(1);
        }

        scanner.close();

        //NEW WORLD
        World world0 = new World(rows, columns, entities);
        world0.printWorld();

        int typesRemaining;

        do {
            typesRemaining = 0;

            // Count the number of entities
            if (Rock.rockCount > 0) typesRemaining++;
            if (Paper.paperCount > 0) typesRemaining++;
            if (Scissors.scissorsCount > 0) typesRemaining++;

            if (typesRemaining <= 1) {
                System.out.println("Game Over.");
                if (Rock.rockCount > 0) {
                    System.out.println("Rock Wins!");
                }
                if (Paper.paperCount > 0) {
                    System.out.println("Paper Wins!");
                }
                if (Scissors.scissorsCount > 0) {
                    System.out.println("Scissors Wins!");
                }
                break;
            }

            world0.playRound();
            world0.printWorld();
            
        } while (typesRemaining > 0);    
    }

}


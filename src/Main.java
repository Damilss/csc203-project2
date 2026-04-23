import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/* Note: Remmeber to account for zero based indexing, if the user inputs a column length of 7, the index is 0-6
*   - I think I dealt with the package handling, but make sure to double check for your IDE. 
*
*
*/

public class Main {
    public static void main(String[] args) throws InterruptedException {

        /*IDE suggests that we should use try-with scanner, not worth the trouble
        * keep it the way it is and just close at the end of Main.main()
        */
        final String INVALID_INTEGER = ("That is not a valid integer.");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Rock Paper Scissors");
        System.out.println("How big would you like your map?");

        //retieving rows and columns from user.
        boolean columnsFlag = false;
        boolean rowsFlag = false;
        boolean entityFlag = false;

        //initalizing int variables since scanner pulls as string, we need to conver to 4 byte int
        int columns= 0; 
        int rows = 0;
        int entities = 0;

        //exception handling for integer
        while (!columnsFlag) {
            System.out.print("Please enter how many columns: ");
            String input = scanner.nextLine().trim();

            try {
                columns = Integer.parseInt(input);
                columnsFlag = true;
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
                rowsFlag = true;
            } catch (NumberFormatException e) {
                System.out.println(INVALID_INTEGER);
            }
        }

        //exception handdling for integer
        while(!entityFlag){
            System.out.print("How many different objects would you like?: ");
            String input3 = scanner.nextLine().trim();

            try {
                entities = Integer.parseInt(input3);
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
        World world1 = new World(rows, columns, entities);

        /* NEXT STEPS: Implement play round until last entity is left or two of the same
        * kind are left, then end program. 
        *
        *
        */

    }
}

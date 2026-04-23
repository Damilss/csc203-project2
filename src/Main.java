import java.util.Scanner;
import java.util.concurrent.TimeUnit;


public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Rock Paper Scissors");
        System.out.println("How big would you like your map?");

        //retieving rows and columns from user.
        boolean columnsFlag = false;
        boolean rowsFlag = false;

        int columns= 0; 
        int rows = 0;

        while (!columnsFlag) {
            System.out.print("Please enter how many columns: ");
            String input = scanner.nextLine().trim();

            try {
                columns = Integer.parseInt(input);
                columnsFlag = true;
            } catch (NumberFormatException e) {
                System.out.println("That is not a valid integer.");
            }
        }

        while(!rowsFlag){
            System.out.print("How many rows?: ");
            String input2 = scanner.nextLine().trim();

            try {
                rows = Integer.parseInt(input2);
                rowsFlag = true;
            } catch (NumberFormatException e) {
                System.out.println("that is not a valid integer.");
            }
        }

        //beginning game! 
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
        World world1 = new World(rows, columns);

    }
}

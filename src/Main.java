import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Rock Paper Scissors");
        System.out.println("How big would you like your map?");
        try { 
            System.out.print("Please enter how many columns: ");
            String input = scanner.nextLine().strip();
            int columns = Integer.parseInt(input);
        } catch (NumberFormatException e){
            boolean integerFlag = false;

            System.out.println("That is not a valid integer.");
            System.out.print("please enter how many columns as an integer: ");
            String input = scanner.nextLine()strip();
        }
        
       
        


        System.out.print("How many rows?: ");
        String rows = scanner.nextLine();
        for (int i= 0;  i < 3;  i++){
            int timer= 3-i;
            if (timer == 3){
                System.out.printf("Starting in %d\n", timer);
            } else {
                System.out.printf("%d\n",timer );
            }
            
            TimeUnit.SECONDS.sleep(1);
        }

        World worldMap = new World(;
        





    }

}

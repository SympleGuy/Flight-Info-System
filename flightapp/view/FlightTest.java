package flightapp.view;

import flightapp.controller.FlightController;

import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class FlightTest {
    // Create static instance of controller
    private final static FlightController controller = new FlightController();
    //Create static instance of Scanner
    private final static Scanner scanner = new Scanner(System.in);

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static void consoleApp() {
        System.out.println("-----------------------WELCOME TO AIRQUEST--------------------");

        //Prompt user to enter number of flight
        System.out.println("How many flights would you like to enter?");
        int numberOfFlights = 0;
        try{
            numberOfFlights = Integer.parseInt(scanner.nextLine());
            if (numberOfFlights <= 0) {
                System.out.println("Number of flights entered must be greater than 0.");
                return;
            }
        } catch (NumberFormatException e){
            System.out.println("Invalid number entered. Must be an integer.");
            return;
        }

        for (int i = 0; i < numberOfFlights; i++) {
            System.out.printf("------------Flight %d------------------\n", i + 1); //Prompt for each flight
        }
    }


}

package flightapp.view;

import flightapp.controller.FlightController;
import flightapp.model.Flight;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.List;
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

        //Prompt user to enter the number of flights
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
            flightMenu();
        }

        //Display flight details before sorting
        System.out.println("\nBefore sorting:");
        displayFlights(controller.getFlights());

        //Display flight details after sorting
        System.out.println("\nAfter sorting:");
        displayFlights(controller.sortedByFlightNumber());
    }

    public static void flightMenu(){
        Flight flight = null;

        while(flight == null){
            try{
                //Read flight name
                System.out.print("Name of the airline: ");
                String airlineName = scanner.nextLine();
                //Read flight number
                System.out.print("Flight number: ");
                String flightNumber = scanner.nextLine();
                //Read flight origin
                System.out.print("Origin: ");
                String origin = scanner.nextLine();
                //Read flight destination
                System.out.print("Destination: ");
                String destination = scanner.nextLine();


                LocalDateTime departureTime = null;
                //Loop until a valid date/time is entered
                while (departureTime == null){
                    System.out.println("Departure time (in 24-hour-time dd-MM-yyyy HH:mm format)");
                    String depTimeStr = scanner.nextLine();
                    try{
                        departureTime = LocalDateTime.parse(depTimeStr, formatter);
                    } catch (DateTimeException e){
                        System.out.println("INVALID: Date time input does not follow 24-hour-time dd-MM-yyyy HH:mm format.");
                        System.out.println("Please re-enter departure time in 24-hour-time dd-MM-yyyy HH:mm format:");
                    }
                }

                LocalDateTime arrivalTime = null;
                //Loop until a valid date/time is entered
                while (arrivalTime == null){
                    System.out.println("Arrival time (in 24-hour-time dd-MM-yyyy HH:mm format)");
                    String arrTimeStr = scanner.nextLine();
                    try{
                        arrivalTime = LocalDateTime.parse(arrTimeStr, formatter);
                    } catch (DateTimeException e){
                        System.out.println("INVALID: Date time input does not follow 24-hour-time dd-MM-yyyy HH:mm format.");
                        System.out.println("Please re-enter arrival time in 24-hour-time dd-MM-yyyy HH:mm format:");
                    }
                }

                //Loop until a valid double is entered
                double airfare = 0;
                while (true){
                    try{
                        System.out.print("Airfare: ");
                        airfare = Double.parseDouble(scanner.nextLine());
                        if (airfare > 0) {
                            break; // Exit loop if the value is valid
                        } else {
                            // Provide specific feedback for invalid value
                            System.out.println("INVALID: Airfare must be greater than 0. Please try again.");
                        }
                    } catch (NumberFormatException e){
                        //Handle an invalid number format
                        System.out.println("INVALID: Airfare must be a number. Please try again.");
                    }
                }

                int availableSeat = 0;
                //Loop until a valid number of seats is entered
                while (true){
                    try{
                        System.out.print("Available seat: ");
                        availableSeat = Integer.parseInt(scanner.nextLine());
                        break;
                    } catch (NumberFormatException e){
                        System.out.println("INVALID: Number of seat must be an Integer. Please try again.");
                    }
                }

                double distance = 0;
                //Loop until a valid double is entered
                while (true){
                    try{
                        System.out.print("Distance: ");
                        distance = Double.parseDouble(scanner.nextLine());
                        break;
                    } catch (NumberFormatException e){
                        System.out.println("INVALID: Distance must be a number. Please try again.");
                    }
                }
                //Create a new flight object
                //Using FlightController to add flight to an ArrayList of flights (easy to manage data)
                flight = new Flight(airlineName,flightNumber,origin,destination,airfare,departureTime,arrivalTime,availableSeat,distance);
                controller.addFlight(flight);
                System.out.print("Flight added successfully.\n");

            } catch (IllegalArgumentException e){
                //Catch validation error
                System.out.println(e.getMessage());
                System.out.println("Please re-enter all details for this flight.");
                //if error, set flight = null to re-enter the menu
                flight = null;
            }
        }


    }
    private static void displayFlights(List<Flight> flightsToDisplay){
        //Check if the list of flight is empty
        if(flightsToDisplay.isEmpty()){
            System.out.println("No flights to display.");
            return;
        }

        for (Flight flight : flightsToDisplay){
            System.out.println(flight.toString());
        }
    }

}

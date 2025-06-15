package flightapp.view;

import flightapp.controller.FlightController;
import flightapp.model.Flight;
import flightapp.utils.ValidationUtils;

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
                String airlineName;
                while(true){
                    try {
                        System.out.println("Name of the airline: ");
                        airlineName = ValidationUtils.validateString(scanner.nextLine(),"Airline Name");
                        break; //Exit loop if valid
                    } catch (IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                }
                //Read flight number
                String flightNumber;
                while(true){
                    try {
                        System.out.println("Flight number: ");
                        flightNumber = ValidationUtils.validateFlightNumber(scanner.nextLine());
                        break;
                    }catch (IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                }
                //Read flight origin
                String origin;
                while(true){
                    try {
                        System.out.println("Origin: ");
                        origin = ValidationUtils.validateString(scanner.nextLine(),"Origin");
                        break;
                    }catch (IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                }
                //Read flight destination
                String destination;
                while(true){
                    try {
                        System.out.println("Destination: ");
                        destination = ValidationUtils.validateString(scanner.nextLine(),"Destination");
                        break;
                    }catch (IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                }


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
                double airfare;
                while (true){
                    try {
                        System.out.println("Airfare: ");
                        double parsedValue = Double.parseDouble(scanner.nextLine());
                        airfare = ValidationUtils.validatePositive(parsedValue, "Airfare");
                        break;
                    }catch (NumberFormatException e){
                        System.out.println("INVALID: Airfare must be a number.");
                    }catch (IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                }

                //Loop until a valid number of seats is entered
                int availableSeat;
                while (true){
                    try {
                        System.out.println("Available Seat: ");
                        int parsedSeat = Integer.parseInt(scanner.nextLine());
                        availableSeat = ValidationUtils.validatePositive(parsedSeat, "AvailableSeat");
                        break;
                    }catch (NumberFormatException e){
                        System.out.println("INVALID: Available Seat must be a number.");
                    }catch (IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                }

                //Loop until a valid double is entered
                double distance = 0;
                while (true){
                    try {
                        System.out.println("Distance: ");
                        double parsedDistance = Double.parseDouble(scanner.nextLine());
                        distance = ValidationUtils.validatePositive(parsedDistance, "Distance");
                        break;
                    }catch (NumberFormatException e){
                        System.out.println("INVALID: Distance must be a number.");
                    }catch (IllegalArgumentException e){
                        System.out.println(e.getMessage());
                    }
                }
                //Create a new flight object
                //Using FlightController to add flight to an ArrayList of flights (easy to manage data)
                flight = new Flight(airlineName,flightNumber,origin,destination,airfare,departureTime,arrivalTime,availableSeat,distance);
                controller.addFlight(flight);
                System.out.println("Flight added successfully.");

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

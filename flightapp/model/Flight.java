package flightapp.model;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class Flight {
    // Create variables
    private String airlineName;
    private String flightNumber;
    private String flightOrigin;
    private String flightDestination;
    private double airfare;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;

    //Additional variables
    private String duration;
    private int availableSeat;
    private double distance;

    //Constructor

    //Getter
    public String getAirlineName() {
        return airlineName;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getFlightOrigin() {
        return flightOrigin;
    }

    public String getFlightDestination() {
        return flightDestination;
    }

    public double getAirfare() {
        return airfare;
    }

    public LocalDateTime getDepartureTime() {
        return departureTime;
    }

    public LocalDateTime getArrivalTime() {
        return arrivalTime;
    }

    public String getDuration() {
        return duration;
    }

    public int getAvailableSeat() {
        return availableSeat;
    }

    public double getDistance() {
        return distance;
    }

    //Setter with validation
    public void setAirlineName(String airlineName) {
        if(airlineName == null || airlineName.trim().isEmpty()) {
            throw new IllegalArgumentException("Airline name cannot be empty"); //Check if airlineName is empty or not
        }
        if(!Pattern.matches("^[a-zA-Z ]+$", airlineName)) {
            throw new IllegalArgumentException("INVALID: Airline name must only contain letters and spaces.\nPlease enter a valid airline name: "); //Check valid regex (only letters and spaces)
        }
        this.airlineName = airlineName.trim(); //remove all unwanted space with trim()
    }

    public void setFlightNumber(String flightNumber) {
        if(flightNumber == null || flightNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Flight number cannot be empty");
        }
        if (!Pattern.matches("^[a-zA-Z0-9]+$", flightNumber)) {
            throw new IllegalArgumentException("INVALID: Flight number must only contain letters and numbers.\nPlease enter a valid flight number: .");
        }
    }

    // toString() method


}

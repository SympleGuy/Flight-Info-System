package flightapp.model;
import java.time.Duration;
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


    public Flight(String airlineName, String flightNumber, String flightOrigin, String flightDestination, double airfare, LocalDateTime departureTime, LocalDateTime arrivalTime, int availableSeat, double distance) {
        //Constructor call setter method to initialize the instance variables, each setter method contains the validation
        //Avoid code duplication

        setAirlineName(airlineName);
        setFlightNumber(flightNumber);
        setFlightOrigin(flightOrigin);
        setFlightDestination(flightDestination);
        setAirfare(airfare);

        setDepartureTime(departureTime);
        setArrivalTime(arrivalTime);

        setAvailableSeat(availableSeat);
        setDistance(distance);
    }

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

    //Function
    private String calDuration(LocalDateTime start, LocalDateTime end) {
        //Check null for input variables
        if (start == null || end == null) {
            return "N/A";
        }

        //Check logic for input variables (end must be after start)
        if (!end.isAfter(start)) {
            return "N/A";
        }
        //Calculate duration
        Duration d = Duration.between(start, end);
        long hours = d.toHours();
        long minutes = d.toMinutesPart(); //Get the minute part of the duration
        return String.format("%dh:%02dm", hours, minutes);
    }

    //Setter with validation
    public void setAirlineName(String airlineName) {
        if (airlineName == null || airlineName.trim().isEmpty()) {
            throw new IllegalArgumentException("Airline name cannot be empty"); //Check if airlineName is empty or not
        }
        if(!Pattern.matches("^[a-zA-Z ]+$", airlineName)) {
            throw new IllegalArgumentException("INVALID: Airline name must only contain letters and spaces.\nPlease enter a valid airline name: "); //Check valid regex (only letters and spaces)
        }
        this.airlineName = airlineName.trim(); //remove all unwanted space with trim()
    }

    public void setFlightNumber(String flightNumber) {
        if (flightNumber == null || flightNumber.trim().isEmpty()) {
            throw new IllegalArgumentException("Flight number cannot be empty"); //Check empty
        }
        if (!Pattern.matches("^[a-zA-Z0-9]+$", flightNumber)) {
            throw new IllegalArgumentException("INVALID: Flight number must only contain letters and numbers.\nPlease enter a valid flight number: "); //Check valid regex (only letters and numbers)
        }
        this.flightNumber = flightNumber.trim();
    }

    public void setFlightOrigin(String flightOrigin) {
        if (flightOrigin == null || flightOrigin.trim().isEmpty()) {
            throw new IllegalArgumentException("Flight origin cannot be empty"); //Check empty
        }
        if(!Pattern.matches("^[a-zA-Z ]+$", flightOrigin)) {
            throw new IllegalArgumentException("INVALID: Flight origin must only contain letters and spaces.\nPlease enter a valid flight origin: "); //Check valid regex (only letters and spaces)
        }
        this.flightOrigin = flightOrigin.trim();
    }

    public void setFlightDestination(String flightDestination) {
        if (flightDestination == null || flightDestination.trim().isEmpty()) {
            throw new IllegalArgumentException("Flight destination cannot be empty"); //Check empty
        }
        if(!Pattern.matches("^[a-zA-Z ]+$", flightDestination)) {
            throw new IllegalArgumentException("INVALID: Flight destination must only contain letters and spaces.\nPlease enter a valid flight destination: "); //Check valid regex (only letters and spaces)
        }
        this.flightDestination = flightDestination.trim();
    }

    public void setAirfare(double airfare) {
        if (airfare <= 0) {
            throw new IllegalArgumentException("Airfare cannot be negative"); //Check value
        }
        this.airfare = airfare;
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        if (departureTime == null) {
            throw new IllegalArgumentException("Departure time cannot be null");
        }
        //Logical check with arrivalTime
        if (this.arrivalTime != null) {
            if (departureTime.isAfter(this.arrivalTime)) {
                throw new IllegalArgumentException("INVALID: Departure time cannot be after arrival time.");
            }
            if (departureTime.isEqual(this.arrivalTime)) {
                throw new IllegalArgumentException("INVALID: Departure time cannot be the same as arrival time.");
            }
        }
        this.departureTime = departureTime;
        //In case you want to update departureTime outside CLI, need to add this line
        // Update duration
        setDuration(calDuration(this.departureTime, this.arrivalTime));
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        if (arrivalTime == null) {
            throw new IllegalArgumentException("Arrival time cannot be null");
        }
//        if (this.departureTime == null) {
//            throw new IllegalArgumentException("Departure time must be set before arrival time.");
//        }
        if (arrivalTime.isBefore(this.departureTime)) {
            throw new IllegalArgumentException("INVALID: Arrival time cannot be before departure time.");
        }
        if (arrivalTime.isEqual(this.departureTime)) {
            throw new IllegalArgumentException("INVALID: Arrival time cannot be the same as departure time.");
        }
        this.arrivalTime = arrivalTime;
        //Update duration
        setDuration(calDuration(this.departureTime, this.arrivalTime));
    }

    //No need calculation (Already call inside setArrivalTime)
    private void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDistance(double distance) {
        if (distance <= 0) {
            throw new IllegalArgumentException("Distance cannot be negative");
        }
        this.distance = distance;
    }

    public void setAvailableSeat(int availableSeat) {
        if (availableSeat <= 0) {
            throw new IllegalArgumentException("Available seat cannot be negative");
        }
        this.availableSeat = availableSeat;
    }

    // toString() method
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"); //create formatter
        // ternary operator -- Check if time is null or not, if not then format
        String formattedDepartureTime = (departureTime != null) ? departureTime.format(formatter) : "N/A";
        String formattedArrivalTime = (arrivalTime != null) ? arrivalTime.format(formatter) : "N/A";



        return "Flight[" +
                "Airline Name='" + airlineName + '\'' +
                ", Flight Number='" + flightNumber + '\'' +
                ", Origin='" + flightOrigin + '\'' +
                ", Destination='" + flightDestination + '\'' +
                ", Airfare='" + String.format("%.2f$", airfare) + '\'' +
                ", Departure Time='" + formattedDepartureTime + '\'' +
                ", Arrival Time='" + formattedArrivalTime + '\'' +
                ", Duration='" + getDuration() + '\'' +
                ", Available Seat=" + availableSeat +
                ", Distance=" + distance +
                ']';
    }
}

package flightapp.model;
import flightapp.utils.ValidationUtils;

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

        //Validate time logic before setting
        ValidationUtils.validateTimeLogic(departureTime, arrivalTime);
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
        this.airlineName = ValidationUtils.validateString(airlineName, "Airline name");
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = ValidationUtils.validateFlightNumber(flightNumber);
    }

    public void setFlightOrigin(String flightOrigin) {
        this.flightOrigin = ValidationUtils.validateString(flightOrigin, "Flight origin");
    }

    public void setFlightDestination(String flightDestination) {
        this.flightDestination = ValidationUtils.validateString(flightDestination, "Flight destination");
    }

    public void setAirfare(double airfare) {
        this.airfare = ValidationUtils.validatePositive(airfare, "Airfare");
    }

    public void setDepartureTime(LocalDateTime departureTime) {
        if (departureTime == null) {
            throw new IllegalArgumentException("Departure time cannot be null");
        }
        this.departureTime = departureTime;
        //In case you want to update departureTime outside CLI, need to add this line
        // Update duration
//        setDuration(calDuration(this.departureTime, this.arrivalTime));
    }

    public void setArrivalTime(LocalDateTime arrivalTime) {
        if (arrivalTime == null) {
            throw new IllegalArgumentException("Arrival time cannot be null");
        }
        ValidationUtils.validateTimeLogic(this.departureTime, arrivalTime);
        this.arrivalTime = arrivalTime;
        //Update duration
        setDuration(calDuration(this.departureTime, this.arrivalTime));
    }

    //No need calculation (Already call inside setArrivalTime)
    private void setDuration(String duration) {
        this.duration = duration;
    }

    public void setDistance(double distance) {
        this.distance = ValidationUtils.validatePositive(distance, "Distance");
    }

    public void setAvailableSeat(int availableSeat) {
        this.availableSeat = ValidationUtils.validatePositive(availableSeat, "Available Seat");
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

package flightapp.controller;

import flightapp.model.Flight;
import java.util.*;
import java.util.stream.Collectors;

public class FlightController {
    //Create variable
    private final List<Flight> flights;

    //Constructor
    public FlightController() {
        this.flights = new ArrayList<>();
    }
    //Add a given flight object to the List
    public void addFlight(Flight flight) {
        if(flight != null) { //Ensure the flight object is not null before adding
            this.flights.add(flight);
        }
    }

    public List<Flight> getFlights() {
        return new ArrayList<>(this.flights);
    }

    public List<Flight> sortedByFlightNumber() {
        List<Flight> sortedFlights = new ArrayList<>(this.flights);

        sortedFlights.sort(Comparator.comparing(Flight::getFlightNumber));
        return sortedFlights;
    }

    public List<Flight> sortedByOthers(String sortByAttribute) {
        List<Flight> sortedFlights = new ArrayList<>(this.flights);

        Comparator<Flight> comparator = null;

        switch(sortByAttribute.toLowerCase()) {
            case "airfare":
                comparator = Comparator.comparing(Flight::getAirfare);
                break;
            case "departuretime":
                comparator = Comparator.comparing(Flight::getDepartureTime);
                break;
            case "arrivaltime":
                comparator = Comparator.comparing(Flight::getArrivalTime);
                break;
            case "flightnumber":
            default:
                comparator = Comparator.comparing(Flight::getFlightNumber);
        }

        return sortedFlights;
    }
}

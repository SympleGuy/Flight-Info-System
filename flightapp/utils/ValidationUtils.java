package flightapp.utils;

import java.time.LocalDateTime;
import java.util.regex.Pattern;


public final class ValidationUtils {
    //Private constructor to prevent instantiation of this utility class
    //All method should be accessed statically
    private ValidationUtils() {}

    //Validation for String input (letters and spaces only)
    public static String validateString(String name, String fieldName){
        if(name == null || name.trim().isEmpty()){
            throw new IllegalArgumentException(fieldName + " cannot be empty.");
        }
        //Regex
        if(!Pattern.matches("^[a-zA-Z ]+$", name)){
            throw new IllegalArgumentException("INVALID: " + fieldName + " must only contain letters and spaces.");
        }
        return name.trim();
    }

    //Validation for flight number (letters and numbers only)
    public static String validateFlightNumber(String flightNumber){
        if (flightNumber == null || flightNumber.trim().isEmpty()){
            throw new IllegalArgumentException("Flight number cannot be empty.");
        }
        //Regex
        if (!Pattern.matches("^[a-zA-Z0-9]+$", flightNumber)){
            throw new IllegalArgumentException("INVALID: Flight number must only contain letters and numbers.");
        }
        return flightNumber.trim();
    }

    //Validation for double and integer input (Overloaded)
    public static double validatePositive(double value, String fieldName){
        if(value <= 0){
            throw new IllegalArgumentException("INVALID: " + fieldName + " must be a positive number.");
        }
        return value;
    }

    public static int validatePositive(int value, String fieldName){
        if(value <= 0){
            throw new IllegalArgumentException("INVALID: " + fieldName + " must be a positive number.");
        }
        return value;
    }

    //Validation for time logic
    public static void validateTimeLogic(LocalDateTime depTime, LocalDateTime arrTime){
        //Check null
        if (depTime == null || arrTime == null) {
            return;
        }
        //Check logic
        if (!arrTime.isAfter(depTime)) {
            throw new IllegalArgumentException("INVALID: Arrival time must be after departure time.");
        }
    }

}

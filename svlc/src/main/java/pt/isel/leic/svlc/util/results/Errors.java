package pt.isel.leic.svlc.util.results;

/**
 * This class represents an error message.
 * It includes a method for creating an error message
 * and a method for converting the error
 * message to a string.
 */
public record Errors (String message){

    public String message() {
        return message;
    }

   @Override
    public String toString() {
        return "[ERROR] " + message;
    }
}

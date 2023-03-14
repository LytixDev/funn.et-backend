package edu.ntnu.idatt2105.placeholder.exceptions;

/**
 * Exception thrown when an error occurs while communicating with the database.
 * @author Callum G.
 * @version 1.0
 * @date 13.3.2023
 */
public class DatabaseException extends Exception {
    
    /**
     * Constructor for DatabaseException.
     * @param message The message to be displayed.
     */
    public DatabaseException(String message) {
        super(message);
    }

    /**
     * Constructor for DatabaseException.
     * Has a default message.
     */
    public DatabaseException() {
        super("An error occurred while communicating with the database.");
    }
}

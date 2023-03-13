package edu.ntnu.idatt2105.placeholder.exceptions;

/**
 * Exception thrown when a user does not exist.
 * @author Callum G.
 * @version 1.0
 * @date 13.3.2023
 */
public class UserDoesNotExistsException extends Exception {

    /**
     * Constructor for UserDoesNotExistsException.
     * @param message The message to be displayed.
     */
    public UserDoesNotExistsException(String message) {
        super(message);
    }

    /**
     * Constructor for UserDoesNotExistsException.
     * Has a default message.
     */
    public UserDoesNotExistsException() {
        super("A user with this username does not exist.");
    }
}

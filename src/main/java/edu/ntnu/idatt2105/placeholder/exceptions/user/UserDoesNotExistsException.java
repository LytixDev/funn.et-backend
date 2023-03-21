package edu.ntnu.idatt2105.placeholder.exceptions.user;

/**
 * Exception thrown when a user does not exist.
 * @author Callum G., Carl G.
 * @version 1.1 - 18.03.2023
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
    super("User does not exist");
  }
}

package edu.ntnu.idatt2105.placeholder.exceptions.user;

/**
 * Exception thrown when a user with a given username already exists.
 * @author Callum G.
 * @version 1.0
 * @date 13.3.2023
 */
public class UsernameAlreadyExistsException extends Exception {

  /**
   * Constructor for UsernameAlreadyExistsException.
   * @param message The message to be displayed.
   */
  public UsernameAlreadyExistsException(String message) {
    super(message);
  }

  /**
   * Constructor for UsernameAlreadyExistsException.
   * Has a default message.
   */
  public UsernameAlreadyExistsException() {
    super("A user with this username already exists.");
  }
}

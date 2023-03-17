package edu.ntnu.idatt2105.placeholder.exceptions.user;

/**
 * Exception thrown when a user with a given email already exists.
 * @author Callum G.
 * @version 1.0
 * @date 13.3.2023
 */
public class EmailAlreadyExistsException extends Exception {

  /**
   * Constructor for EmailAlreadyExistsException.
   * @param message The message to be displayed.
   */
  public EmailAlreadyExistsException(String message) {
    super(message);
  }

  /**
   * Constructor for EmailAlreadyExistsException.
   * Has a default message.
   */
  public EmailAlreadyExistsException() {
    super("A user with this email already exists.");
  }
}

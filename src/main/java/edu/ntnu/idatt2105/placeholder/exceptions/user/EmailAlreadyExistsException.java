package edu.ntnu.idatt2105.placeholder.exceptions.user;

/**
 * Exception thrown when a user with a given email already exists.
 * @author Callum G., Carl G.
 * @version 1.1 - 18.03.2023
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
    super("userEmailAlreadyExists");
  }
}

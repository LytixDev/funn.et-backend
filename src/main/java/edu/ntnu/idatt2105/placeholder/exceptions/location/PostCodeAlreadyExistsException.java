package edu.ntnu.idatt2105.placeholder.exceptions.location;

/**
 * Exception thrown when a postcode already exists in the database.
 * @author Callum G.
 * @version 1.0 - 17.3.2023
 */
public class PostCodeAlreadyExistsException extends Exception {

  /**
   * Constructor for PostCodeAlreadyExistsException.
   * @param message The message to be displayed.
   */
  public PostCodeAlreadyExistsException(String message) {
    super(message);
  }

  /**
   * Constructor for PostCodeAlreadyExistsException.
   * Has a default message.
   */
  public PostCodeAlreadyExistsException() {
    super("postCodeAlreadyExists");
  }
}

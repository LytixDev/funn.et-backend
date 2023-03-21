package edu.ntnu.idatt2105.placeholder.exceptions.location;

/**
 * Exception thrown when a postcode doesn't exist in the database.
 * @author Callum G.
 * @version 1.0 - 17.3.2023
 */
public class PostCodeDoesntExistException extends Exception {

  /**
   * Constructor for PostCodeDoesntExistException.
   * @param message The message to be displayed.
   */
  public PostCodeDoesntExistException(String message) {
    super(message);
  }

  /**
   * Constructor for PostCodeDoesntExistException.
   * Has a default message.
   */
  public PostCodeDoesntExistException() {
    super("Post code does not exist.");
  }
}

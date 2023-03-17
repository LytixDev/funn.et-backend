package edu.ntnu.idatt2105.placeholder.exceptions.location;

/**
 * Exception thrown when a location already exists in the database.
 * @author Callum G.
 * @version 1.0 - 17.3.2023
 */
public class LocationAlreadyExistsException extends Exception {

  /**
   * Constructor for LocationAlreadyExistsException.
   * @param message The message to be displayed.
   */
  public LocationAlreadyExistsException(String message) {
    super(message);
  }

  /**
   * Constructor for LocationAlreadyExistsException.
   * Has a default message.
   */
  public LocationAlreadyExistsException() {
    super("Location already exists in the database.");
  }
}

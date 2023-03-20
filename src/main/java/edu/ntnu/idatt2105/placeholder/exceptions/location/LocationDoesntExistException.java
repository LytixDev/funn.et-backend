package edu.ntnu.idatt2105.placeholder.exceptions.location;

/**
 * Exception thrown when a location doesn't exist in the database.
 * @author Callum G.
 * @version 1.0 - 17.3.2023
 */
public class LocationDoesntExistException extends Exception {

  /**
   * Constructor for LocationDoesntExistException.
   * @param message The message to be displayed.
   */
  public LocationDoesntExistException(String message) {
    super(message);
  }

  /**
   * Constructor for LocationDoesntExistException.
   * Has a default message.
   */
  public LocationDoesntExistException() {
    super("locationDoesntExist");
  }
}

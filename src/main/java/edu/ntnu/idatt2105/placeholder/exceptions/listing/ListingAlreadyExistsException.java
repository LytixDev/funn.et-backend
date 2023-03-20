package edu.ntnu.idatt2105.placeholder.exceptions.listing;

/**
 * Exception thrown when a listing is not found.
 * @author Callum G.
 * @version 1.0 - 18.03.2023
 */
public class ListingAlreadyExistsException extends Exception {

  /**
   * Constructor for the exception.
   * @param message The message to be displayed.
   */
  public ListingAlreadyExistsException(String message) {
    super(message);
  }

  /**
   * Constructor for the exception.
   * Has a default message.
   */
  public ListingAlreadyExistsException() {
    super("Listing already exists");
  }
}

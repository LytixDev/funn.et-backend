package edu.ntnu.idatt2105.funn.exceptions.validation;

/**
 * Exception thrown when the user inputs an invalid search.
 * @author Callum G.
 * @version 1.0 - 27.03.2023
 */
public class BadSearchException extends Exception {

  /**
   * Constructor for BadSearchException.
   * @param message The message to be displayed.
   */
  public BadSearchException(String message) {
    super(message);
  }

  /**
   * The message to be displayed when the exception is thrown.
   */
  public BadSearchException() {
    super("Bad search.");
  }
}

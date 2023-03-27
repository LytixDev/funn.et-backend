package edu.ntnu.idatt2105.funn.exceptions;

/**
 * Exception thrown when the user inputs invalid data.
 * @author Callum G.
 * @version 1.0 - 27.03.2023
 */
public class BadInputException extends Exception {

  /**
   * The message to be displayed when the exception is thrown.
   */
  public BadInputException() {
    super("Bad input.");
  }
}

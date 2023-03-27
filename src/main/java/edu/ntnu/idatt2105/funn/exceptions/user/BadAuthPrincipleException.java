package edu.ntnu.idatt2105.funn.exceptions.user;

/**
 * Exception thrown when the Auth object is invalid.
 * @author Callum G.
 * @version 1.0 - 27.03.2023
 */
public class BadAuthPrincipleException extends Exception {

  /**
   * Constructor for BadAuthPrinciple.
   * @param message The message to be displayed.
   */
  public BadAuthPrincipleException() {
    super("The Auth object is invalid.");
  }
}

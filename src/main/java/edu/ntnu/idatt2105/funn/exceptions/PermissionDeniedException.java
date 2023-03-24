package edu.ntnu.idatt2105.funn.exceptions;

/**
 * Exception thrown when a user does not have permission to perform an action.
 * @author Nicolai H. B.
 * @version 1.0 - 24.3.2023
 */
public class PermissionDeniedException extends Exception {

  /**
   * Constructor for PermissionDeniedException.
   * @param message The message to be displayed.
   */
  public PermissionDeniedException(String message) {
    super(message);
  }

  /**
   * Constructor for PermissionDeniedException.
   * Has a default message: 'Permission denied'.
   */
  public PermissionDeniedException() {
    super("Permission denied");
  }
}

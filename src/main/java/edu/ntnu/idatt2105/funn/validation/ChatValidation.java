package edu.ntnu.idatt2105.funn.validation;

/**
 * Class for chat validation.
 * Used for validation of user input.
 * @author Callum G.
 * @version 1.0 - 27.03.2023
 */
public class ChatValidation extends Validation {
    
  /**
   * Validate a message.
   * @param message The message to validate.
   * @return True if the message is valid, false otherwise.
   */
  public static boolean validateMessage(String message) {
    return isNotNullOrEmpty(message) && isSmallerThan(message, 255);
  }
}

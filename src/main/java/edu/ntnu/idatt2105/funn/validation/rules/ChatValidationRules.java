package edu.ntnu.idatt2105.funn.validation.rules;

/**
 * Class for chat validation rules.
 * Used for validation of user input.
 * Describes the minimum and maximum values for certain fields.
 * @author Callum G.
 * @version 1.0 - 27.03.2023
 */
public enum ChatValidationRules {
  /**
   * Maximum length of a chat message.
   */
  MESSAGE_MAX_LENGTH(255);

  private final int value;

  /**
   * Constructor for ChatValidationRules.
   * @param value The value to be used.
   */
  ChatValidationRules(int value) {
    this.value = value;
  }

  /**
   * Getter for value.
   * @return The value.
   */
  public int getValue() {
    return value;
  }
}

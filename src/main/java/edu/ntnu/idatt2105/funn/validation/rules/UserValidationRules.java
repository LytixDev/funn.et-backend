package edu.ntnu.idatt2105.funn.validation.rules;

/**
 * Enum for validation rules.
 * Used for validation of user input.
 * Describes the minimum and maximum values for certain fields.
 * @author Callum G.
 * @version 1.0 - 27.03.2023
 */
public enum UserValidationRules {
  /**
   * Minimum length of a password.
   */
  PASSWORD_MIN_LENGTH(8),
  /**
   * Minimum length of a username.
   */
  USERNAME_MIN_LENGTH(3),
  /**
   * Maximum length of a username.
   */
  USERNAME_MAX_LENGTH(32),
  /**
   * Minimum length of a name.
   */
  NAME_MIN_LENGTH(1),
  /**
   * Maximum length of a name.
   */
  NAME_MAX_LENGTH(64),
  /**
   * Minimum length of an email.
   */
  EMAIL_MIN_LENGTH(1),
  /**
   * Maximum length of an email.
   */
  EMAIL_MAX_LENGTH(64);

  private final int value;

  /**
   * Constructor for UserValidationRules.
   * @param value The value to be used.
   */
  UserValidationRules(int value) {
    this.value = value;
  }

  /**
   * Getter for the value.
   * @return The value.
   */
  public int getValue() {
    return value;
  }
}

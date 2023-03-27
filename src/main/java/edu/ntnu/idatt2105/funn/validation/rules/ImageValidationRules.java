package edu.ntnu.idatt2105.funn.validation.rules;

/**
 * Enum for image validation rules.
 * Used for validation of user input.
 * Describes the minimum and maximum values for certain fields.
 * @author Callum G.
 * @version 1.0 - 27.03.2023
 */
public enum ImageValidationRules {
  /**
   * Maximum amount of images.
   */
  IMAGE_AMOUNT_MAX(50),
  /**
   * Maximum size of an image.
   * 10 Megabytes.
   */
  IMAGE_SIZE_MAX(10485760),
  /**
   * Maximum length of an image alt text.
   */
  IMAGE_ALT_MAX_LENGTH(255);

  private final int value;

  /**
   * Constructor for ImageValidationRules.
   * @param value The value to be used.
   */
  ImageValidationRules(int value) {
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

package edu.ntnu.idatt2105.funn.validation.rules;

/**
 * Enum for search request validation rules.
 * Used for validation of user input.
 * Describes the minimum and maximum values for certain fields.
 * @author Callum G.
 * @version 1.0 - 27.03.2023
 */
public enum SearchRequestValidationRules {
  /**
   * Minimum size for a page.
   */
  PAGE_MIN_SIZE(1),
  /**
   * Maximum size for a page.
   */
  PAGE_MAX_SIZE(100),
  /**
   * Minimum value for a page number.
   */
  PAGE_MIN_NUMBER(0);

  private final int value;

  /**
   * Constructor for SearchRequestValidationRules.
   * @param value The value to be used.
   */
  SearchRequestValidationRules(int value) {
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

package edu.ntnu.idatt2105.funn.validation.rules;

public enum ValidationRules {
  /**
   * Minimum index of a database entry.
   */
  DATABASE_MIN_INDEX(1);

  /**
   * The value of the rule.
   */
  private final int value;

  /**
   * Constructor for ValidationRules.
   * @param value The value of the rule.
   */
  ValidationRules(int value) {
    this.value = value;
  }

  /**
   * Getter for value.
   * @return The value of the rule.
   */
  public int getValue() {
    return value;
  }
}

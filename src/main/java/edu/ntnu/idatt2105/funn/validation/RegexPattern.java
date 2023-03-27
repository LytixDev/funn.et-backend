package edu.ntnu.idatt2105.funn.validation;

/**
 * Enum for regex patterns.
 * @author Callum G.
 * @version 1.0 - 27.03.2023
 */
public enum RegexPattern {
  /**
   * Email regex pattern.
   * Must be of characters a-z, A-Z, 0-9, +, _, -, . and {@literal @}.
   */
  EMAIL("^[A-Za-z0-9+_.-]+@(.+)$"),
  /**
   * Password regex pattern.
   * Must contain 1 uppercase letter, 1 lowercase letter, 1 number and be at least 8 characters long.
   */
  PASSWORD("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{9,64}$"),
  /**
   * Username regex pattern.
   * Must be of characters a-z, A-Z, 0-9, ., _, - and be between 3 and 32 characters long.
   * Must not start or end with ., _, -.
   * Must not contain two or more consecutive ., _, -.
   */
  USERNAME("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){1,30}[a-zA-Z0-9]$"),
  /**
   * Name regex pattern.
   * Must be of characters a-z, A-Z, ', ., -.
   * Must not start or end with ', ., -.
   * Must not contain two or more consecutive ', ., -.
   */
  NAME("^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$"),
  /**
   * Address regex pattern.
   * Must be of characters a-z, A-Z, 0-9, ', . and -.
   * Must not start or end with ', ., -.
   * Must not contain two or more consecutive ', ., -.
   */
  ADDRESS("^[a-zA-Z0-9]+(([',. -][a-zA-Z0-9 ])?[a-zA-Z0-9]*)*$"),
  /**
   * Java variable name regex pattern.
   * Must be of characters a-z, A-Z, 0-9 and _.
   * Must start with a lowercase letter or _.
   * Allows for $, as this is legal in Java.
   */
  JAVA_VARIABLE("^[_$a-z][\\w$]*$");

  /**
   * The pattern to be used.
   */
  private final String pattern;

  /**
   * Constructor for RegexPattern.
   * @param pattern The pattern to be used.
   */
  RegexPattern(String pattern) {
    this.pattern = pattern;
  }

  /**
   * Get the pattern.
   * @return The pattern.
   */
  public String getPattern() {
    return pattern;
  }
}

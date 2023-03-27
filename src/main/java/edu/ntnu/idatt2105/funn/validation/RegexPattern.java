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
   * Must contain {@literal @} and at least one character after {@literal @}.
   */
  EMAIL(
    "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$"
  ),
  /**
   * Password regex pattern.
   * Must contain 1 uppercase letter, 1 lowercase letter, 1 number.
   */
  PASSWORD("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$"),
  /**
   * Username regex pattern.
   * Atleast 3 characters.
   * Must be of characters a-z, A-Z, 0-9, ., _, -.
   * Must not start or end with ., _, -.
   * Must not contain two or more consecutive ., _, -.pr
   */
  USERNAME("^[a-zA-Z0-9]([._-](?![._-])|[a-zA-Z0-9]){3,}[a-zA-Z0-9]$"),
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

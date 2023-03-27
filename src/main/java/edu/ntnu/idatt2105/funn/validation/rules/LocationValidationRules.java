package edu.ntnu.idatt2105.funn.validation.rules;

/**
 * Enum for location validation rules.
 * Used for validation of user input.
 * Describes the minimum and maximum values for certain fields.
 * @author Callum G.
 * @version 1.0 - 27.03.2023
 */
public enum LocationValidationRules {
  /**
   * Minimum length of an address.
   */
  ADDRESS_MIN_LENGTH(1),
  /**
   * Maximum length of an address.
   */
  ADDRESS_MAX_LENGTH(255),
  /**
   * Minimum value of a postcode in Norway, as of 27.03.2023.
   */
  POSTCODE_MIN_VALUE(1),
  /**
   * Maximum value of a postcode in Norway, as of 27.03.2023.
   */
  POSTCODE_MAX_VALUE(9991),
  /**
   * Minimum length of a city name.
   * In Norway, the shortest city name is "Å" (Aurland).
   */
  CITY_MIN_LENGTH(1),
  /**
   * Maximum length of a city name.
   * In Norway, the longest place name is "Øvraørnefjeddstakkslåttå" (Åseral).
   */
  CITY_MAX_LENGTH(24),
  /**
   * Longitude minimum value.
   */
  LONGITUDE_MIN_VALUE(-180),
  /**
   * Longitude maximum value.
   */
  LONGITUDE_MAX_VALUE(180),
  /**
   * Latitude minimum value.
   */
  LATITUDE_MIN_VALUE(-90),
  /**
   * Latitude maximum value.
   */
  LATITUDE_MAX_VALUE(90);

  private final int value;

  /**
   * Constructor for LocationValidationRules.
   * @param value The value to be used.
   */
  LocationValidationRules(int value) {
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

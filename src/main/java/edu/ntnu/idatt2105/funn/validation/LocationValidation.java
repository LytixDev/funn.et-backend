package edu.ntnu.idatt2105.funn.validation;

import edu.ntnu.idatt2105.funn.validation.rules.LocationValidationRules;

/**
 * Class for location validation.
 * @author Callum G.
 * @version 1.0 - 27.03.2023
 */
public class LocationValidation extends BaseValidation {

  /**
   * Validate an address.
   * @param address The address to validate.
   * @return True if the address is valid, false otherwise.
   */
  public static boolean validateAddress(String address) {
    return (
      isNotNullOrEmpty(address) &&
      address.matches(RegexPattern.ADDRESS.getPattern()) &&
      isBetween(
        address,
        LocationValidationRules.ADDRESS_MIN_LENGTH.getValue(),
        LocationValidationRules.ADDRESS_MAX_LENGTH.getValue()
      )
    );
  }

  /**
   * Validate a coordinate.
   * @param longitude The longitude to validate.
   * @param latitude  The latitude to validate.
   * @return True if the coordinate is valid, false otherwise.
   */
  public static boolean validateCoordinate(double longitude, double latitude) {
    return (
      isBetween(
        latitude,
        LocationValidationRules.LATITUDE_MIN_VALUE.getValue(),
        LocationValidationRules.LATITUDE_MAX_VALUE.getValue()
      ) &&
      isBetween(
        longitude,
        LocationValidationRules.LONGITUDE_MIN_VALUE.getValue(),
        LocationValidationRules.LONGITUDE_MAX_VALUE.getValue()
      )
    );
  }

  /**
   * Validate a post code.
   * For Norwegian post codes as they are 4 digits long and positive.
   * @param postcode The post code to validate.
   * @return True if the post code is valid, false otherwise.
   */
  public static boolean validatePostcode(int postcode) {
    return isBetween(
      postcode,
      LocationValidationRules.POSTCODE_MIN_VALUE.getValue(),
      LocationValidationRules.POSTCODE_MAX_VALUE.getValue()
    );
  }

  /**
   * Validate a city name.
   * 24 characters is the maximum length of a city name in Norway.
   * @param city The city name to validate.
   * @return True if the city name is valid, false otherwise.
   */
  public static boolean validateCity(String city) {
    return (
      isNotNullOrEmpty(city) &&
      city.matches(RegexPattern.NAME.getPattern()) &&
      isBetween(
        city,
        LocationValidationRules.CITY_MIN_LENGTH.getValue(),
        LocationValidationRules.CITY_MAX_LENGTH.getValue()
      )
    );
  }

  /**
   * Validate a location.
   * @param address   The address to validate.
   * @param latitude  The latitude to validate.
   * @param longitude The longitude to validate.
   * @param postCode  The post code to validate.
   * @param city      The city to validate.
   * @return True if the location is valid, false otherwise.
   */
  public static boolean validateLocation(
    String address,
    double latitude,
    double longitude,
    int postCode,
    String city
  ) {
    boolean valid = true;
    valid &= validateAddress(address);
    valid &= validateCoordinate(longitude, latitude);
    valid &= validatePostcode(postCode);
    valid &= validateCity(city);

    return valid;
  }
}

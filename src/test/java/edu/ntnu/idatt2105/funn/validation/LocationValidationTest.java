package edu.ntnu.idatt2105.funn.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

public class LocationValidationTest {

  private final String emptyString = "";
  private final String longString =
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage";

  private final String goodAddress = "address";
  private final String badAddress = "address&&&address!";

  private final int goodPostalCode = 1234;
  private final int badPostalCode = 12345;

  private final String goodCity = "city";
  private final String badCity = "city&&&city!";

  private final double goodLatitude = 23.123;
  private final double badLatitude = 234.1234;

  private final double goodLongitude = 123.123;
  private final double badLongitude = 1234.1234;

  @Test
  public void testAddressValidateReturnsTrue() {
    assertTrue(LocationValidation.validateAddress(goodAddress));
  }

  @Test
  public void testAddressValidateReturnsFalse() {
    assertFalse(LocationValidation.validateAddress(badAddress));
  }

  @Test
  public void testAddressValidateReturnsFalseLongString() {
    assertFalse(LocationValidation.validateAddress(longString));
  }

  @Test
  public void testAddressValidateReturnsFalseEmptyString() {
    assertFalse(LocationValidation.validateAddress(emptyString));
  }

  @Test
  public void testPostalCodeValidateReturnsTrue() {
    assertTrue(LocationValidation.validatePostcode(goodPostalCode));
  }

  @Test
  public void testPostalCodeValidateReturnsFalse() {
    assertFalse(LocationValidation.validatePostcode(badPostalCode));
  }

  @Test
  public void testCityValidateReturnsTrue() {
    assertTrue(LocationValidation.validateCity(goodCity));
  }

  @Test
  public void testCityValidateReturnsFalse() {
    assertFalse(LocationValidation.validateCity(badCity));
  }

  @Test
  public void testCityValidateReturnsFalseLongString() {
    assertFalse(LocationValidation.validateCity(longString));
  }

  @Test
  public void testCityValidateReturnsFalseEmptyString() {
    assertFalse(LocationValidation.validateCity(emptyString));
  }

  @Test
  public void testLatitudeLongitudeValidateReturnsTrue() {
    assertTrue(LocationValidation.validateCoordinate(goodLongitude, goodLatitude));
  }

  @Test
  public void testLatitudeValidateReturnsFalse() {
    assertFalse(LocationValidation.validateCoordinate(goodLongitude, badLatitude));
  }

  @Test
  public void testLongitudeValidateReturnsFalse() {
    assertFalse(LocationValidation.validateCoordinate(badLongitude, goodLatitude));
  }
}

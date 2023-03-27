package edu.ntnu.idatt2105.funn.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;
import org.junit.Test;

public class ListingValidationTest {

  private final String emptyString = "";
  private final String longString =
    "message message message" +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message" +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message" +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message " +
    "message message message ";

  private final String goodCategoryName = "category";

  private final String goodDescription = "description";

  private final double goodPrice = 100.0;
  private final double badPrice = -100.0;

  private final LocalDate dateNow = LocalDate.now();
  private final LocalDate dateAfter = LocalDate.now().plusMonths(1);

  @Test
  public void testCategoryNameValidateReturnsTrue() {
    assertTrue(ListingValidation.validateCategoryName(goodCategoryName));
  }
  
  @Test
  public void testCategoryNameValidateReturnsFalseOnEmptyString() {
    assertFalse(ListingValidation.validateCategoryName(emptyString));
  }

  @Test
  public void testCategoryNameValidateReturnsFalseOnLongString() {
    assertFalse(ListingValidation.validateCategoryName(longString));
  }

  @Test
  public void testFullDescriptionValidateReturnsTrue() {
    assertTrue(ListingValidation.validateFullDescription(goodDescription));
  }

  @Test
  public void testFullDescriptionValidateReturnsFalseOnEmptyString() {
    assertFalse(ListingValidation.validateFullDescription(emptyString));
  }

  @Test
  public void testFullDescriptionValidateReturnsFalseOnLongString() {
    assertFalse(ListingValidation.validateFullDescription(longString));
  }

  @Test
  public void testBriefDescriptionValidateReturnsTrue() {
    assertTrue(ListingValidation.validateBriefDescription(goodDescription));
  }

  @Test
  public void testBriefDescriptionValidateReturnsFalseOnEmptyString() {
    assertFalse(ListingValidation.validateBriefDescription(emptyString));
  }

  @Test
  public void testBriefDescriptionValidateReturnsFalseOnLongString() {
    assertFalse(ListingValidation.validateBriefDescription(longString));
  }

  @Test
  public void testPriceValidateReturnsTrue() {
    assertTrue(ListingValidation.validatePrice(goodPrice));
  }

  @Test
  public void testPriceValidateReturnsFalse() {
    assertFalse(ListingValidation.validatePrice(badPrice));
  }

  @Test
  public void testDateValidateNewReturnsTrue() {
    assertTrue(ListingValidation.validateDatesNew(dateNow, dateAfter));
  }

  @Test
  public void testDateValidateNewReturnsFalse() {
    assertFalse(ListingValidation.validateDatesNew(dateAfter, dateNow));
  }

  @Test
  public void testDateValidateNewReturnsFalseOnSameDates() {
    assertFalse(ListingValidation.validateDatesNew(dateNow, dateNow));
  }

  @Test
  public void testDateValidateNewReturnsFalseOnOldPublicationDate() {
    assertFalse(ListingValidation.validateDatesNew(dateAfter, dateAfter));
  }

  @Test
  public void testDateValidateNewReturnsFalseOnNullDates() {
    assertFalse(ListingValidation.validateDatesNew(null, null));
  }

  @Test
  public void testDateValidateUpdateReturnsTrue() {
    assertTrue(ListingValidation.validateDatesUpdate(dateNow, dateAfter));
  }

  @Test
  public void testDateValidateUpdateReturnsFalse() {
    assertFalse(ListingValidation.validateDatesUpdate(dateAfter, dateNow));
  }

  @Test
  public void testDateValidateUpdateReturnsFalseOnSameDates() {
    assertFalse(ListingValidation.validateDatesUpdate(dateNow, dateNow));
  }
}

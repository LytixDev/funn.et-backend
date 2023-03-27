package edu.ntnu.idatt2105.funn.validation;

import edu.ntnu.idatt2105.funn.validation.rules.ListingValidationRules;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

/**
 * Class for listing validation.
 * @author Callum G.
 * @version 1.0 - 27.03.2023
 */
public class ListingValidation extends BaseValidation {

  /**
   * Validate category name.
   * @param categoryName
   * @return True if the category name is valid, false otherwise.
   */
  public static boolean validateCategoryName(String categoryName) {
    return (
      isNotNullOrEmpty(categoryName) &&
      categoryName.matches(RegexPattern.NAME.getPattern()) &&
      isSmallerThan(categoryName, ListingValidationRules.CATEGORY_MAX_LENGTH.getValue())
    );
  }

  /**
   * Validate a category.
   * @param categoryName The category name to validate.
   * @return True if the category is valid, false otherwise.
   */
  public static boolean validateCategory(String categoryName) {
    return validateCategory(categoryName);
  }

  /**
   * Validate a title.
   * @param title The title to validate.
   * @return True if the title is valid, false otherwise.
   */
  public static boolean validateTitle(String title) {
    return (
      isNotNullOrEmpty(title) &&
      isSmallerThan(title, ListingValidationRules.TITLE_MAX_LENGTH.getValue())
    );
  }

  /**
   * Validate a full description.
   * @param description The description to validate.
   * @return True if the description is valid, false otherwise.
   */
  public static boolean validateFullDescription(String description) {
    return (
      isNotNullOrEmpty(description) &&
      isSmallerThan(description, ListingValidationRules.FULL_DESCRIPTION_MAX_LENGTH.getValue())
    );
  }

  /**
   * Validate a brief description.
   * @param description The description to validate.
   * @return True if the description is valid, false otherwise.
   */
  public static boolean validateBriefDescription(String description) {
    return (
      isNotNullOrEmpty(description) &&
      isSmallerThan(description, ListingValidationRules.BRIEF_DESCRIPTION_MAX_LENGTH.getValue())
    );
  }

  /**
   * Validate a price.
   * @param price The price to validate.
   * @return True if the price is valid, false otherwise.
   */
  public static boolean validatePrice(double price) {
    return (
      isBetween(
        price,
        ListingValidationRules.PRICE_MIN_VALUE.getValue(),
        ListingValidationRules.PRICE_MAX_VALUE.getValue()
      )
    );
  }

  /**
   * Validate dates.
   * @param publicationDate The publication date to validate.
   * @param expirationDate The expiration date to validate.
   * @return True if the date is valid, false otherwise.
   */
  public static boolean validateDatesUpdate(LocalDate publicationDate, LocalDate expirationDate) {
    return (
      publicationDate != null && expirationDate != null && isAfter(expirationDate, publicationDate)
    );
  }

  /**
   * Validate new dates.
   * @param publicationDate The publication date to validate.
   * @param expirationDate The expiration date to validate.
   * @return True if the date is valid, false otherwise.
   */
  public static boolean validateDatesNew(LocalDate publicationDate, LocalDate expirationDate) {
    return (
      publicationDate != null &&
      expirationDate != null &&
      isAfter(publicationDate, LocalDate.now().minus(Period.ofDays(1))) &&
      isAfter(expirationDate, publicationDate)
    );
  }

  /**
   * Validate a listing to be created.
   * @param title The title to validate.
   * @param briefDescription The brief description to validate.
   * @param fullDescription The full description to validate.
   * @param price The price to validate.
   * @param publicationDate The publication date to validate.
   * @param expirationDate The expiration date to validate.
   * @param images The images to validate.
   * @param imageAlts The image alts to validate.
   */
  public static boolean validateListing(
    String title,
    String briefDescription,
    String fullDescription,
    double price,
    LocalDate publicationDate,
    LocalDate expirationDate,
    MultipartFile[] images,
    String[] imageAlts
  ) {
    boolean valid = true;
    valid &= validateTitle(title);
    valid &= validateBriefDescription(briefDescription);
    valid &= validateFullDescription(fullDescription);
    valid &= validatePrice(price);
    valid &= validateDatesNew(publicationDate, expirationDate);
    if (isNotNullOrEmpty(images)) valid &= ImageValidation.validateImages(images);
    if (isNotNullOrEmpty(imageAlts)) valid &= ImageValidation.validateImageAlts(imageAlts);

    return valid;
  }

  /**
   * Validate a listing to be updated.
   * @param username The username to validate.
   * @param title The title to validate.
   * @param briefDescription The brief description to validate.
   * @param fullDescription The full description to validate.
   * @param price The price to validate.
   * @param publicationDate The publication date to validate.
   * @param expirationDate The expiration date to validate.
   * @param images Images to keep.
   * @param images The images to validate.
   * @param imageAlts The image alts to validate.
   */
  public static boolean validateListingUpdate(
    String username,
    String title,
    String briefDescription,
    String fullDescription,
    double price,
    LocalDate publicationDate,
    LocalDate expirationDate,
    List<Long> imagesToKeep,
    MultipartFile[] images,
    String[] imageAlts
  ) {
    boolean valid = true;
    valid &= UserValidation.validateUsername(username);
    valid &=
      validateListing(
        title,
        briefDescription,
        fullDescription,
        price,
        publicationDate,
        expirationDate,
        images,
        imageAlts
      );
    valid &= validatePositive(imagesToKeep.toArray(Long[]::new));

    return valid;
  }
}

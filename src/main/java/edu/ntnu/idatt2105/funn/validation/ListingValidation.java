package edu.ntnu.idatt2105.funn.validation;

import java.time.LocalDate;
import java.time.Period;

import org.apache.commons.lang3.EnumUtils;
import org.springframework.web.multipart.MultipartFile;

import edu.ntnu.idatt2105.funn.model.file.ImageFileTypes;
import edu.ntnu.idatt2105.funn.validation.rules.ListingValidationRules;

/**
 * Class for listing validation.
 * @author Callum G.
 * @version 1.0 - 27.03.2023
 */
public class ListingValidation extends Validation {
    
    /**
     * Validate category name.
     * @param categoryName
     * @return True if the category name is valid, false otherwise.
     */
    public static boolean validateCategoryName(String categoryName) {
        return (
        isNotNullOrEmpty(categoryName) &&
        categoryName.matches(RegexPattern.NAME.getPattern()) &&
            isSmallerThan(categoryName, ListingValidationRules.CATEGORY_MAX_LENGTH.getValue()
        )
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
            isSmallerThan(title, ListingValidationRules.TITLE_MAX_LENGTH.getValue()
        )
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
            isBetween(price, ListingValidationRules.PRICE_MIN_VALUE.getValue(), ListingValidationRules.PRICE_MAX_VALUE.getValue())
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
        publicationDate != null && expirationDate != null &&
            isAfter(expirationDate, publicationDate)
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
     * Validate an image.
     * @param image The image to validate.
     * @return True if the image is valid, false otherwise.
     */
    public static boolean validateImage(MultipartFile image) {
        return image != null && EnumUtils.isValidEnum(ImageFileTypes.class, image.getContentType()) && isSmallerThan(image.getSize(), ListingValidationRules.IMAGE_SIZE_MAX.getValue());
    }

    /**
     * Validates images.
     * @param images The images to validate.
     * @return True if the quantity is valid, false otherwise.
     */
    public static boolean validateImages(MultipartFile[] images) {
        boolean valid = true;
        for (MultipartFile image : images) {
            if (!validateImage(image)) {
                valid = false;
                break;
            }
        }
        return (
            isSmallerThanOrEqual(images.length, ListingValidationRules.IMAGE_AMOUNT_MAX.getValue())
        )
    }
}

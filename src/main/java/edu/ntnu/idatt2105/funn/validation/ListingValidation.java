package edu.ntnu.idatt2105.funn.validation;

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
        title.matches(RegexPattern.NAME.getPattern()) &&
            isSmallerThan(title, ListingValidationRules.TITLE_MAX_LENGTH.getValue()
        )
        );
    }
}

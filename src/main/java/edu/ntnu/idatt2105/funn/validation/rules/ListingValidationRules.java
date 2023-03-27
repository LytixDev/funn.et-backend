package edu.ntnu.idatt2105.funn.validation.rules;

/**
 * Class for listing validation rules.
 * Used for validation of user input.
 * Describes the minimum and maximum values for certain fields.
 * @author Callum G.
 * @version 1.0 - 27.03.2023
 */
public enum ListingValidationRules {
    
    /**
     * Minimum length of a title.
     */
    TITLE_MIN_LENGTH(1),
    /**
     * Maximum length of a title.
     */
    TITLE_MAX_LENGTH(64),
    /**
     * Minimum length of a description.
     */
    FULL_DESCRIPTION_MIN_LENGTH(1),
    /**
     * Maximum length of a description.
     */
    FULL_DESCRIPTION_MAX_LENGTH(512),
    /**
     * Minimum length of a brief description.
     */
    BRIEF_DESCRIPTION_MIN_LENGTH(1),
    /**
     * Maximum length of a brief description.
     */
    BRIEF_DESCRIPTION_MAX_LENGTH(255),
    /**
     * Minimum value of a price.
     */
    PRICE_MIN_VALUE(0),
    /**
     * Maximum value of a price.
     */
    PRICE_MAX_VALUE(100000000),
    /**
     * Maximum value of a quantity.
     */
    CATEGORY_MAX_LENGTH(255),
    /**
     * Maximum amount of images.
     */
    IMAGE_AMOUNT_MAX(5),
    /**
     * Maximum size of an image.
     */
    IMAGE_SIZE_MAX(5242880);

    private final int value;

    /**
     * Constructor for ListingValidaitonRules.
     * @param value The value to be used.
     */
    ListingValidationRules(int value) {
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

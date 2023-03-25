package edu.ntnu.idatt2105.funn.exceptions.listing;

/**
 * Exception for when a category is not found.
 * @author Callum G.
 * @version 1.0 - 25.03.2023
 */
public class CategoryNotFoundException extends Exception {
    
    /**
     * Constructor for CategoryNotFoundException
     */
    public CategoryNotFoundException() {
        super("Category not found");
    }
}

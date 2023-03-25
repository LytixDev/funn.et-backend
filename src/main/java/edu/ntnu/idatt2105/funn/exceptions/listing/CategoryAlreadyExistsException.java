package edu.ntnu.idatt2105.funn.exceptions.listing;

/**
 * Exception for when a category already exists.
 * @author Callum G.
 * @version 1.0 - 25.03.2023
 */
public class CategoryAlreadyExistsException extends Exception {

  /**
   * Constructor for CategoryAlreadyExistsException
   */
  public CategoryAlreadyExistsException() {
    super("Category already exists");
  }
}

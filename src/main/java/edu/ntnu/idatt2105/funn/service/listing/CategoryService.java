package edu.ntnu.idatt2105.funn.service.listing;

import edu.ntnu.idatt2105.funn.exceptions.listing.CategoryAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.listing.CategoryNotFoundException;
import edu.ntnu.idatt2105.funn.model.listing.Category;
import java.util.List;

/**
 * Interface for service layer for Category
 * @author Callum G.
 * @version 1.0 - 25.03.2023
 */
public interface CategoryService {
  List<Category> getAllCategories();

  Category getCategoryById(Long id) throws CategoryNotFoundException;

  Category createCategory(Category category) throws CategoryAlreadyExistsException;

  Category updateCategory(Category category) throws CategoryNotFoundException;

  void deleteCategory(Long id) throws CategoryNotFoundException;
}

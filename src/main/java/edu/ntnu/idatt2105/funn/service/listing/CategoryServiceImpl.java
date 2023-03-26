package edu.ntnu.idatt2105.funn.service.listing;

import edu.ntnu.idatt2105.funn.exceptions.listing.CategoryAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.listing.CategoryNotFoundException;
import edu.ntnu.idatt2105.funn.model.listing.Category;
import edu.ntnu.idatt2105.funn.repository.listing.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service layer for Category
 * @author Callum G.
 * @version 1.0 - 25.03.2023
 */
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  /**
   * Get all categories
   * @return List of all categories
   */
  @Override
  public List<Category> getAllCategories() {
    return categoryRepository.findAll();
  }

  /**
   * Get category by id
   * @param id
   * @return Category
   */
  @Override
  public Category getCategoryById(Long id) throws CategoryNotFoundException {
    return categoryRepository.findById(id).orElseThrow(CategoryNotFoundException::new);
  }

  /**
   * Create category if it does not already exist
   * @param category Category to create
   * @return created category
   * @throws CategoryAlreadyExistsException if category already exists
   */
  @Override
  public Category createCategory(Category category) throws CategoryAlreadyExistsException {
    if (category.getId() != null) {
      throw new CategoryAlreadyExistsException();
    }

    if (categoryRepository.existsByName(category.getName())) {
      throw new CategoryAlreadyExistsException();
    }

    return categoryRepository.save(category);
  }

  /**
   * Update category
   * @param category Category to update
   * @return updated category
   * @throws CategoryNotFoundException if category does not exist
   */
  @Override
  public Category updateCategory(Category category) throws CategoryNotFoundException {
    if (!categoryRepository.existsById(category.getId())) {
      throw new CategoryNotFoundException();
    }

    return categoryRepository.save(category);
  }

  /**
   * Delete category
   * @param id Id of category to delete
   * @throws CategoryNotFoundException if category does not exist
   */
  @Override
  public void deleteCategory(Long id) throws CategoryNotFoundException {
    if (!categoryRepository.existsById(id)) {
      throw new CategoryNotFoundException();
    }

    categoryRepository.deleteById(id);
  }
}

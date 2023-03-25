package edu.ntnu.idatt2105.funn.controller.listing;

import edu.ntnu.idatt2105.funn.dto.listing.CategoryDTO;
import edu.ntnu.idatt2105.funn.exceptions.listing.CategoryAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.listing.CategoryNotFoundException;
import edu.ntnu.idatt2105.funn.mapper.listing.CategoryMapper;
import edu.ntnu.idatt2105.funn.service.listing.CategoryService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for category.
 * Mappings for getting all, getting one,
 * creating, updating and deleting categories.
 * @author Callum G.
 * @version 1.0 - 25.3.2023
 */
@RestController
@EnableAutoConfiguration
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryController {

  private static final Logger LOGGER = LoggerFactory.getLogger(CategoryController.class);

  private final CategoryService categoryService;

  /**
   * Get all categories
   * @return List of all categories
   */
  @GetMapping("/public/categories")
  public ResponseEntity<List<CategoryDTO>> getAllCategories() {
    LOGGER.info("Getting all categories");

    List<CategoryDTO> categories = categoryService
      .getAllCategories()
      .stream()
      .map(category -> CategoryMapper.INSTANCE.categoryToCategoryDTO(category))
      .collect(Collectors.toList());

    LOGGER.info("Returning all categories");

    return ResponseEntity.ok(categories);
  }

  /**
   * Get category by id
   * @param id
   * @return Category
   */
  @GetMapping("/public/categories/{id}")
  public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id)
    throws CategoryNotFoundException {
    LOGGER.info("Getting category with id: {}", id);

    CategoryDTO category = CategoryMapper.INSTANCE.categoryToCategoryDTO(
      categoryService.getCategoryById(id)
    );

    LOGGER.info("Returning category with id: {}", id);

    return ResponseEntity.ok(category);
  }

  /**
   * Create category if it does not already exist
   * @param category Category to create
   * @return created category
   * @throws CategoryAlreadyExistsException if category already exists
   */
  @PostMapping("/private/categories")
  public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO category)
    throws CategoryAlreadyExistsException {
    LOGGER.info("Creating category with name: {}", category.getName());

    CategoryDTO createdCategory = CategoryMapper.INSTANCE.categoryToCategoryDTO(
      categoryService.createCategory(CategoryMapper.INSTANCE.categoryDTOToCategory(category))
    );

    LOGGER.info("Returning created category with name: {}", category.getName());

    return ResponseEntity.ok(createdCategory);
  }

  /**
   * Update category if it exists
   * @param category Category to update
   * @return updated category
   * @throws CategoryNotFoundException if category does not exist
   */
  @PostMapping("/private/categories/{id}")
  public ResponseEntity<CategoryDTO> updateCategory(
    @PathVariable Long id,
    @RequestBody CategoryDTO category
  ) throws CategoryNotFoundException {
    LOGGER.info("Updating category with id: {}", id);

    if (!id.equals(category.getId())) throw new CategoryNotFoundException();

    CategoryDTO updatedCategory = CategoryMapper.INSTANCE.categoryToCategoryDTO(
      categoryService.updateCategory(CategoryMapper.INSTANCE.categoryDTOToCategory(category))
    );

    LOGGER.info("Returning updated category with id: {}", id);

    return ResponseEntity.ok(updatedCategory);
  }

  /**
   * Delete category if it exists
   * @param id Id of category to delete
   * @throws CategoryNotFoundException if category does not exist
   */
  @DeleteMapping("/private/categories/{id}")
  public ResponseEntity<Void> deleteCategory(@PathVariable Long id)
    throws CategoryNotFoundException {
    LOGGER.info("Deleting category with id: {}", id);

    categoryService.deleteCategory(id);

    LOGGER.info("Deleted category with id: {}", id);

    return ResponseEntity.noContent().build();
  }
}

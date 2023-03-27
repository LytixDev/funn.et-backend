package edu.ntnu.idatt2105.funn.controller.listing;

import edu.ntnu.idatt2105.funn.dto.listing.CategoryCreateDTO;
import edu.ntnu.idatt2105.funn.dto.listing.CategoryDTO;
import edu.ntnu.idatt2105.funn.exceptions.BadInputException;
import edu.ntnu.idatt2105.funn.exceptions.PermissionDeniedException;
import edu.ntnu.idatt2105.funn.exceptions.listing.CategoryAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.listing.CategoryNotFoundException;
import edu.ntnu.idatt2105.funn.mapper.listing.CategoryMapper;
import edu.ntnu.idatt2105.funn.model.user.Role;
import edu.ntnu.idatt2105.funn.security.Auth;
import edu.ntnu.idatt2105.funn.service.listing.CategoryService;
import edu.ntnu.idatt2105.funn.validation.AuthValidation;
import edu.ntnu.idatt2105.funn.validation.ListingValidation;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for category.
 * Mappings for getting all, getting one,
 * creating, updating and deleting categories.
 * @author Callum G.
 * @version 1.1 - 27.3.2023
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
  @GetMapping(value = "/public/categories", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Gets all categories", description = "Gets all categories that are available in the database.")
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
  @GetMapping(value = "/public/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Gets a category by id.", description = "Gets a category by id that is given if it is available in the database.")
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
  @PostMapping(
    value = "/private/categories",
    consumes = { MediaType.APPLICATION_JSON_VALUE },
    produces = { MediaType.APPLICATION_JSON_VALUE }
  )
  @Operation(summary = "Creates a category.", description = "Creates a category if it does not already exist in the database.")
  public ResponseEntity<CategoryDTO> createCategory(
    @RequestBody CategoryCreateDTO category,
    @AuthenticationPrincipal Auth auth
  ) throws CategoryAlreadyExistsException, BadInputException {
    LOGGER.info("Auth: {}", auth);

    if (!AuthValidation.hasRole(auth, Role.ADMIN)) 
      throw new AccessDeniedException("You do not have permission to create a category");

    if (!ListingValidation.validateCategory(category.getName())) 
      throw new BadInputException("The category name is invalid");

    LOGGER.info("Creating category with name: {}", category);

    CategoryDTO createdCategory = CategoryMapper.INSTANCE.categoryToCategoryDTO(
      categoryService.createCategory(CategoryMapper.INSTANCE.categoryCreateDTOToCategory(category))
    );

    LOGGER.info("Returning created category with name: {}", category);

    return ResponseEntity.ok(createdCategory);
  }

  /**
   * Update category if it exists
   * @param category Category to update
   * @return updated category
   * @throws CategoryNotFoundException if category does not exist
   * @throws BadInputException if category name is invalid
   * @throws PermissionDeniedException if user does is not an admin
   */
  @PutMapping(
    value = "/private/categories/{id}",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  @Operation(summary = "Updates a category.", description = "Updates a category if it exists in the database.")
  public ResponseEntity<CategoryDTO> updateCategory(
    @PathVariable Long id,
    @RequestBody CategoryDTO category,
    @AuthenticationPrincipal Auth auth
  ) throws CategoryNotFoundException, BadInputException, PermissionDeniedException {
    LOGGER.info("Auth: {}", auth);

    if (!AuthValidation.hasRole(auth, Role.ADMIN)) 
      throw new AccessDeniedException("You do not have permission to update a category");

    if (!ListingValidation.validateCategory(category.getName())) 
      throw new BadInputException("The category name is invalid");

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
   * @throws PermissionDeniedException if user is not an admin
   */
  @DeleteMapping(value = "/private/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @Operation(summary = "Deletes a category.", description = "Deletes a category if it exists in the database.")
  public ResponseEntity<String> deleteCategory(
    @PathVariable Long id,
    @AuthenticationPrincipal Auth auth
  ) throws CategoryNotFoundException, PermissionDeniedException {
    LOGGER.info("Auth: {}", auth);

    if (!AuthValidation.hasRole(auth, Role.ADMIN)) 
      throw new AccessDeniedException("You do not have permission to delete a category");

    LOGGER.info("Deleting category with id: {}", id);

    categoryService.deleteCategory(id);

    LOGGER.info("Deleted category with id: {}", id);

    return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Image deleted");
  }
}

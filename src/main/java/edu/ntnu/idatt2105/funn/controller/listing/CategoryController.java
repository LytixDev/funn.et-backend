package edu.ntnu.idatt2105.funn.controller.listing;

import edu.ntnu.idatt2105.funn.dto.listing.CategoryDTO;
import edu.ntnu.idatt2105.funn.mapper.listing.CategoryMapper;
import edu.ntnu.idatt2105.funn.service.listing.CategoryService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    return ResponseEntity.ok(categoryService.getAllCategories());
  }
}

package edu.ntnu.idatt2105.funn.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import edu.ntnu.idatt2105.funn.exceptions.listing.CategoryAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.listing.CategoryNotFoundException;
import edu.ntnu.idatt2105.funn.model.listing.Category;
import edu.ntnu.idatt2105.funn.repository.listing.CategoryRepository;
import edu.ntnu.idatt2105.funn.service.listing.CategoryService;
import edu.ntnu.idatt2105.funn.service.listing.CategoryServiceImpl;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
public class CategoryServiceIntegrationTest {

  @TestConfiguration
  static class CategoryServiceIntegrationTestContextConfiguration {

    @Bean
    public CategoryService categoryService() {
      return new CategoryServiceImpl();
    }
  }

  @Autowired
  private CategoryService categoryService;

  @MockBean
  private CategoryRepository categoryRepository;

  private Category existingCategory;

  private Category newCategory;

  @Before
  public void setUp() {
    existingCategory = Category.builder().id(1L).name("ExistingCategory").build();

    newCategory = Category.builder().id(2L).name("NewCategory").build();

    when(categoryRepository.existsById(existingCategory.getId())).thenReturn(true);
    when(categoryRepository.existsById(newCategory.getId())).thenReturn(false);

    when(categoryRepository.save(existingCategory)).thenReturn(existingCategory);
    when(categoryRepository.save(newCategory)).thenReturn(newCategory);

    when(categoryRepository.findById(existingCategory.getId()))
      .thenReturn(java.util.Optional.of(existingCategory));
    when(categoryRepository.findById(newCategory.getId())).thenReturn(java.util.Optional.empty());

    when(categoryRepository.findAll()).thenReturn(List.of(existingCategory));

    doNothing().when(categoryRepository).delete(existingCategory);
    doNothing().when(categoryRepository).delete(newCategory);
  }

  @Test
  public void testGetAllCategories() {
    assertFalse(categoryService.getAllCategories().isEmpty());
    assertTrue(categoryService.getAllCategories().contains(existingCategory));
    assertFalse(categoryService.getAllCategories().contains(newCategory));
  }

  @Test
  public void testCategorySaveNewCategory() {
    Category found;
    try {
      found = categoryService.createCategory(newCategory);
    } catch (Exception e) {
      e.printStackTrace();
      fail();
      return;
    }
    assertEquals(found.getId(), newCategory.getId());
    assertEquals(found.getName(), newCategory.getName());
  }

  @Test
  public void testCategorySaveExistingCategory() {
    assertThrows(
      CategoryAlreadyExistsException.class,
      () -> categoryService.createCategory(existingCategory)
    );
  }

  @Test
  public void testGetCategoryExistingCategory() {
    Category found;
    try {
      found = categoryService.getCategoryById(existingCategory.getId());
    } catch (Exception e) {
      fail();
      return;
    }
    assertEquals(found.getId(), existingCategory.getId());
    assertEquals(found.getName(), existingCategory.getName());
  }

  @Test
  public void testGetCategoryNewCategory() {
    assertThrows(
      CategoryNotFoundException.class,
      () -> categoryService.getCategoryById(newCategory.getId())
    );
  }

  @Test
  public void testUpdateCategoryExistingCategory() {
    Category found;
    try {
      found = categoryService.updateCategory(existingCategory);
    } catch (Exception e) {
      fail();
      return;
    }
    assertEquals(found.getId(), existingCategory.getId());
    assertEquals(found.getName(), existingCategory.getName());
  }

  @Test
  public void testUpdateCategoryNewCategory() {
    assertThrows(
      CategoryNotFoundException.class,
      () -> categoryService.updateCategory(newCategory)
    );
  }

  @Test
  public void testDeleteCategoryExistingCategory() {
    try {
      categoryService.deleteCategory(existingCategory.getId());
    } catch (Exception e) {
      fail();
      return;
    }
  }

  @Test
  public void testDeleteCategoryNewCategory() {
    try {
      categoryService.deleteCategory(newCategory.getId());
      fail();
    } catch (Exception e) {
      return;
    }
  }
}

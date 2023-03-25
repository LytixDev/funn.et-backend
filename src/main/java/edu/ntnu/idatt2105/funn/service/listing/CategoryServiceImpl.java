package edu.ntnu.idatt2105.funn.service.listing;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.ntnu.idatt2105.funn.exceptions.listing.CategoryNotFoundException;
import edu.ntnu.idatt2105.funn.model.listing.Category;
import edu.ntnu.idatt2105.funn.repository.listing.CategoryRepository;
import lombok.RequiredArgsConstructor;

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
        if (categoryRepository.existsById(category.getId())) {
            throw new CategoryAlreadyExistsException();
        }
        return categoryRepository.save(category);
    }

}

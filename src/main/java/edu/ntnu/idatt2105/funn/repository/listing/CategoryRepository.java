package edu.ntnu.idatt2105.funn.repository.listing;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.ntnu.idatt2105.funn.model.listing.Category;

/**
 * Interface for repository layer for Category
 * @author Callum G.
 * @version 1.0 - 25.03.2023
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>  {
    
    boolean existsByCategoryName(String categoryName);
}

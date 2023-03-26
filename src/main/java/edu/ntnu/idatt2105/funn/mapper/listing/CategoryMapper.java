package edu.ntnu.idatt2105.funn.mapper.listing;

import edu.ntnu.idatt2105.funn.dto.listing.CategoryCreateDTO;
import edu.ntnu.idatt2105.funn.dto.listing.CategoryDTO;
import edu.ntnu.idatt2105.funn.model.listing.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * Interface for mapping Category to CategoryDTO and vice versa
 * @author Callum G.
 * @version 1.0 - 25.03.2023
 */
@Mapper(componentModel = "spring")
public interface CategoryMapper {
  CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

  /**
   * Maps a CategoryDTO to a Category
   * @param categoryDTO CategoryDTO to map
   * @return the mapped Category
   */
  @Mapping(target = "listings", ignore = true)
  Category categoryDTOToCategory(CategoryDTO categoryDTO);

  /**
   * Maps a CategoryCreateDTO to a Category
   * @param categoryCreateDTO CategoryCreateDTO to map
   * @return the mapped Category
   */
  @Mappings(
    { @Mapping(target = "id", ignore = true), @Mapping(target = "listings", ignore = true) }
  )
  Category categoryCreateDTOToCategory(CategoryCreateDTO categoryCreateDTO);

  /**
   * Maps a Category to a CategoryDTO
   * @param category Category to map
   * @return the mapped CategoryDTO
   */
  CategoryDTO categoryToCategoryDTO(Category category);
}

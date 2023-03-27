package edu.ntnu.idatt2105.funn.mapper.user;

import edu.ntnu.idatt2105.funn.dto.user.RegisterDTO;
import edu.ntnu.idatt2105.funn.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * Class used to map between User and RegisterDTO.
 * @author Carl G.
 * @version 1.0 - 20.3.2023
 */
@Mapper(componentModel = "spring")
public interface RegisterMapper {
  RegisterMapper INSTANCE = Mappers.getMapper(RegisterMapper.class);

  /**
   * Maps a registration DTO to a user.
   * @param registerDTO A user registration in the form of a DTO
   * @return A user object with the USER role
   */
  @Mappings(
    {
      @Mapping(target = "role", constant = "USER"),
      @Mapping(target = "listings", ignore = true),
      @Mapping(target = "chats", ignore = true),
      @Mapping(target = "messages", ignore = true),
      @Mapping(target = "favoriteListings", ignore = true),
    }
  )
  User registerDTOtoUser(RegisterDTO registerDTO);
}

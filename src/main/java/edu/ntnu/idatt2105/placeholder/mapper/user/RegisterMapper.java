package edu.ntnu.idatt2105.placeholder.mapper.user;

import edu.ntnu.idatt2105.placeholder.dto.user.RegisterDTO;
import edu.ntnu.idatt2105.placeholder.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
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
  @Mapping(target = "role", constant = "USER")
  User registerDTOtoUser(RegisterDTO registerDTO);
}

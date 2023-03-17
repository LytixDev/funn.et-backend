package edu.ntnu.idatt2105.placeholder.mapper.user;

import edu.ntnu.idatt2105.placeholder.dto.user.UserDTO;
import edu.ntnu.idatt2105.placeholder.model.user.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Class used to map between User and UserDTO.
 * @author Callum G.
 * @version 1.0 - 13.3.2023
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
  UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

  /**
   * Maps a User to a UserDTO.
   * Ignores the password field.
   * @param user The User to map.
   * @return The mapped UserDTO.
   */
  UserDTO userToUserDTO(User user);
}

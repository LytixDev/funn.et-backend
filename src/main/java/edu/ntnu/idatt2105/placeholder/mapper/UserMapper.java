package edu.ntnu.idatt2105.placeholder.mapper;

import edu.ntnu.idatt2105.placeholder.dto.UserDTO;
import edu.ntnu.idatt2105.placeholder.model.User;

import org.mapstruct.Mapper;

/**
 * Class used to map between User and UserDTO.
 * @author Callum G.
 * @version 1.0
 * @date 13.3.2023
 */
@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Maps a User to a UserDTO.
     * Ignores the password field.
     * @param user The User to map.
     * @return The mapped UserDTO.
     */
    @Mapping(target = "password", ignore = true)
    UserDTO userToUserDTO(User user);
}

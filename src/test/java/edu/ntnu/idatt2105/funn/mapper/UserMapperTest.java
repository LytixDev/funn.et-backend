package edu.ntnu.idatt2105.funn.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2105.funn.dto.user.UserDTO;
import edu.ntnu.idatt2105.funn.mapper.user.UserMapper;
import edu.ntnu.idatt2105.funn.model.user.Role;
import edu.ntnu.idatt2105.funn.model.user.User;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class UserMapperTest {

  @Nested
  public class UserToUserDTOTest {

    @Test
    public void testUserToUserDTO() {
      User user = new User();
      user.setUsername("username");
      user.setEmail("email");
      user.setFirstName("firstName");
      user.setLastName("lastName");
      user.setPassword("password");
      user.setRole(Role.USER);

      UserDTO userDTO = UserMapper.INSTANCE.userToUserDTO(user);

      assertEquals(user.getUsername(), userDTO.getUsername());
      assertEquals(user.getEmail(), userDTO.getEmail());
      assertEquals(user.getFirstName(), userDTO.getFirstName());
      assertEquals(user.getLastName(), userDTO.getLastName());
      assertEquals(user.getRole(), userDTO.getRole());
    }
  }
}

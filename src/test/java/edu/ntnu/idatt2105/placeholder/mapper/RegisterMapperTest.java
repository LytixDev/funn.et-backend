package edu.ntnu.idatt2105.placeholder.mapper;

import static org.junit.Assert.assertEquals;

import edu.ntnu.idatt2105.placeholder.dto.user.RegisterDTO;
import edu.ntnu.idatt2105.placeholder.mapper.user.RegisterMapper;
import edu.ntnu.idatt2105.placeholder.model.user.User;
import org.junit.Test;
import org.junit.jupiter.api.Nested;

public class RegisterMapperTest {

  @Nested
  public class RegisterDTOToUserTest {

    @Test
    public void testUserToUserDTOConvertsToRoleUSER() {
      RegisterDTO registration = RegisterDTO
        .builder()
        .username("username")
        .email("email@example.com")
        .firstName("firstName")
        .lastName("lastName")
        .password("password")
        .build();

      User user = RegisterMapper.INSTANCE.registerDTOtoUser(registration);

      assertEquals(registration.getUsername(), user.getUsername());
      assertEquals(registration.getEmail(), user.getEmail());
      assertEquals(registration.getFirstName(), user.getFirstName());
      assertEquals(registration.getLastName(), user.getLastName());
      assertEquals("USER", user.getRole());
    }
  }
}

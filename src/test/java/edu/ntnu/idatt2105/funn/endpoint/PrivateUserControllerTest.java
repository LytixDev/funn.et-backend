package edu.ntnu.idatt2105.funn.endpoint;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import edu.ntnu.idatt2105.funn.controller.user.PrivateUserController;
import edu.ntnu.idatt2105.funn.model.user.Role;
import edu.ntnu.idatt2105.funn.model.user.User;
import edu.ntnu.idatt2105.funn.security.SecurityConfig;
import edu.ntnu.idatt2105.funn.service.listing.ListingService;
import edu.ntnu.idatt2105.funn.service.user.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest({ PrivateUserController.class, SecurityConfig.class })
public class PrivateUserControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private UserService userService;

  @MockBean
  private ListingService listingService;

  @Test
  @WithMockUser
  public void testGetUserThatExists() throws Exception {
    User user = User
      .builder()
      .username("test")
      .password("test")
      .email("test@test")
      .role(Role.USER)
      .firstName("test")
      .lastName("test")
      .listings(null)
      .build();

    when(userService.getUserByUsername("test")).thenReturn(user);

    try {
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/api/v1/private/user/me")
            .with(SecurityMockMvcRequestPostProcessors.user(user))
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
    } catch (Exception e) {
      fail();
    }
  }
  // @Test
  // @WithMockUser
  // public void testGetUserThatDoesntExist() throws Exception {
  //   when(userService.getUserByUsername("test"))
  //     .thenThrow(new UserDoesNotExistsException());

  //   try {
  //     mvc
  //       .perform(
  //         MockMvcRequestBuilders
  //           .get("/api/v1/private/user/me")
  //           .with(SecurityMockMvcRequestPostProcessors.user("test"))
  //           .accept(MediaType.APPLICATION_JSON)
  //       )
  //       .andExpect(status().isNotFound());
  //   } catch (Exception e) {
  //     fail();
  //   }
  // }
}

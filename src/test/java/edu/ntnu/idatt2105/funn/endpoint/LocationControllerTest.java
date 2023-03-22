package edu.ntnu.idatt2105.funn.endpoint;

import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import edu.ntnu.idatt2105.funn.controller.location.LocationController;
import edu.ntnu.idatt2105.funn.dto.location.LocationCreateDTO;
import edu.ntnu.idatt2105.funn.exceptions.DatabaseException;
import edu.ntnu.idatt2105.funn.exceptions.location.LocationAlreadyExistsException;
import edu.ntnu.idatt2105.funn.exceptions.location.LocationDoesntExistException;
import edu.ntnu.idatt2105.funn.exceptions.location.PostCodeAlreadyExistsException;
import edu.ntnu.idatt2105.funn.mapper.location.LocationMapper;
import edu.ntnu.idatt2105.funn.model.location.Location;
import edu.ntnu.idatt2105.funn.model.location.PostCode;
import edu.ntnu.idatt2105.funn.security.SecurityConfig;
import edu.ntnu.idatt2105.funn.service.location.LocationService;
import edu.ntnu.idatt2105.funn.service.location.PostCodeService;
import java.util.ArrayList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest({ LocationController.class, SecurityConfig.class })
public class LocationControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private LocationService locationService;

  @MockBean
  private PostCodeService postCodeService;

  @Test
  public void testGetLocations() throws DatabaseException {
    when(locationService.getAllLocations()).thenReturn(new ArrayList<>());

    try {
      mvc
        .perform(
          MockMvcRequestBuilders.get("/api/v1/public/locations").accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetLocationExists()
    throws LocationDoesntExistException, DatabaseException, NullPointerException, PostCodeAlreadyExistsException {
    Location location = Location
      .builder()
      .id(1l)
      .address("address")
      .postCode(new PostCode(1445, "city", null))
      .latitude(1.0)
      .longitude(1.0)
      .build();

    when(locationService.getLocationById(1l)).thenReturn(location);
    when(postCodeService.savePostCode(location.getPostCode())).thenReturn(location.getPostCode());

    try {
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/api/v1/public/locations/1")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testGetLocationNonExistent() throws LocationDoesntExistException, DatabaseException {
    when(locationService.getLocationById(1l)).thenThrow(new LocationDoesntExistException());

    try {
      mvc
        .perform(
          MockMvcRequestBuilders
            .get("/api/v1/public/locations/1")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNotFound());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  @WithMockUser
  public void testCreateLocation()
    throws NullPointerException, LocationAlreadyExistsException, DatabaseException, PostCodeAlreadyExistsException {
    Location location = Location
      .builder()
      .id(1l)
      .address("address")
      .postCode(new PostCode(1445, "city", null))
      .latitude(1.0)
      .longitude(1.0)
      .build();

    LocationCreateDTO locationCreateDTO = new LocationCreateDTO("address", 1.0, 1.0, 1445, "city");

    when(
      locationService.saveLocation(
        LocationMapper.INSTANCE.locationCreateDTOToLocation(locationCreateDTO)
      )
    )
      .thenReturn(location);

    when(postCodeService.savePostCode(location.getPostCode())).thenReturn(location.getPostCode());

    try {
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/api/v1/private/locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "{\"address\": \"address\", \"latitude\": 1.0, \"longitude\": 1.0, \"postCode\": 1445, \"city\": \"city\"}"
            )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testCreateLocationWithoutAuthentication() {
    try {
      mvc
        .perform(
          MockMvcRequestBuilders
            .post("/api/v1/private/locations")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "{\"address\": \"address\", \"latitude\": 1.0, \"longitude\": 1.0, \"postCode\": 1445, \"city\": \"city\"}"
            )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isForbidden());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  @WithMockUser
  public void testUpdateLocation()
    throws NullPointerException, LocationAlreadyExistsException, DatabaseException, PostCodeAlreadyExistsException, LocationDoesntExistException {
    Location location = Location
      .builder()
      .id(1l)
      .address("address")
      .postCode(new PostCode(1445, "city", null))
      .latitude(1.0)
      .longitude(1.0)
      .build();

    when(locationService.updateLocation(location)).thenReturn(location);

    when(postCodeService.savePostCode(location.getPostCode())).thenReturn(location.getPostCode());

    try {
      mvc
        .perform(
          MockMvcRequestBuilders
            .put("/api/v1/private/locations/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "{\"id\": 1, \"address\": \"address\", \"latitude\": 1.0, \"longitude\": 1.0, \"postCode\": 1445, \"city\": \"city\"}"
            )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testUpdateLocationWithoutAuthentication() {
    try {
      mvc
        .perform(
          MockMvcRequestBuilders
            .put("/api/v1/private/locations/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(
              "{\"id\": 1, \"address\": \"address\", \"latitude\": 1.0, \"longitude\": 1.0, \"postCode\": 1445, \"city\": \"city\"}"
            )
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isForbidden());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  @WithMockUser
  public void testDeleteLocation() throws LocationDoesntExistException, DatabaseException {
    doNothing().when(locationService).deleteLocation(1l);

    try {
      mvc
        .perform(
          MockMvcRequestBuilders
            .delete("/api/v1/private/locations/1")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isNoContent());
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void testDeleteLocationWithoutAuthentication() {
    try {
      mvc
        .perform(
          MockMvcRequestBuilders
            .delete("/api/v1/private/locations/1")
            .accept(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isForbidden());
    } catch (Exception e) {
      fail();
    }
  }
}

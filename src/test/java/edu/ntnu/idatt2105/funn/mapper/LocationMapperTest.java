package edu.ntnu.idatt2105.funn.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import edu.ntnu.idatt2105.funn.dto.location.LocationCreateDTO;
import edu.ntnu.idatt2105.funn.dto.location.LocationResponseDTO;
import edu.ntnu.idatt2105.funn.mapper.location.LocationMapper;
import edu.ntnu.idatt2105.funn.model.location.Location;
import edu.ntnu.idatt2105.funn.model.location.PostCode;
import org.junit.Test;

public class LocationMapperTest {

  @Test
  public void testLocationToLocationResponseDTOMapsCorrectly() {
    Location location = Location
      .builder()
      .id(1L)
      .address("address")
      .latitude(1.0)
      .longitude(1.0)
      .postCode(new PostCode(1234, "city", null))
      .build();

    LocationResponseDTO locationDTO = LocationMapper.INSTANCE.locationToLocationResponseDTO(
      location
    );

    assertEquals(location.getId(), locationDTO.getId());
    assertEquals(location.getAddress(), locationDTO.getAddress());
    assertEquals(location.getPostCode().getPostCode(), locationDTO.getPostCode());
    assertEquals(location.getPostCode().getCity(), locationDTO.getCity());
    assertEquals(location.getLatitude(), locationDTO.getLatitude());
    assertEquals(location.getLongitude(), locationDTO.getLongitude());
  }

  @Test
  public void testLocationResponseDTOMapsCorrectlyToLocation() {
    LocationResponseDTO locationDTO = new LocationResponseDTO(
      1L,
      "address",
      1.0,
      1.0,
      1234,
      "city"
    );

    Location location = LocationMapper.INSTANCE.locationResponseDTOToLocation(locationDTO);

    assertEquals(locationDTO.getId(), location.getId());
    assertEquals(locationDTO.getAddress(), location.getAddress());
    assertEquals(locationDTO.getLatitude(), location.getLatitude());
    assertEquals(locationDTO.getLongitude(), location.getLongitude());
  }

  @Test
  public void testLocationCreateDTOMapsCorrectlyToLocation() {
    LocationCreateDTO locationDTO = new LocationCreateDTO("address", 1.0, 1.0, 1234, "city");

    Location location = LocationMapper.INSTANCE.locationCreateDTOToLocation(locationDTO);

    assertEquals(locationDTO.getAddress(), location.getAddress());
    assertEquals(locationDTO.getLatitude(), location.getLatitude());
    assertEquals(locationDTO.getLongitude(), location.getLongitude());
  }

  @Test
  public void testLocationCreateDTOMapsCorrectlyToPostCode() {
    LocationCreateDTO locationDTO = new LocationCreateDTO("address", 1.0, 1.0, 1234, "city");

    PostCode postCode = LocationMapper.INSTANCE.locationCreateDTOToPostCode(locationDTO);

    assertEquals(locationDTO.getPostCode(), postCode.getPostCode());
    assertEquals(locationDTO.getCity(), postCode.getCity());
  }

  @Test
  public void testLocationResponseDTOMapsCorrectlyToPostCode() {
    LocationResponseDTO locationDTO = new LocationResponseDTO(
      1L,
      "address",
      1.0,
      1.0,
      1234,
      "city"
    );

    PostCode postCode = LocationMapper.INSTANCE.locationResponseDTOToPostCode(locationDTO);

    assertEquals(locationDTO.getPostCode(), postCode.getPostCode());
    assertEquals(locationDTO.getCity(), postCode.getCity());
  }
}

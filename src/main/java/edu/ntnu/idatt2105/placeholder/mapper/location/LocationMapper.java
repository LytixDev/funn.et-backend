package edu.ntnu.idatt2105.placeholder.mapper.location;

import edu.ntnu.idatt2105.placeholder.dto.location.LocationCreateDTO;
import edu.ntnu.idatt2105.placeholder.dto.location.LocationResponseDTO;
import edu.ntnu.idatt2105.placeholder.model.location.Location;
import edu.ntnu.idatt2105.placeholder.model.location.PostCode;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class LocationMapper {

  LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);

  /**
   * Gets the post code from a postcode.
   * @param postCode The postcode to get the post code from.
   * @return The post code of the postcode.
   */
  @Named("getPostCode")
  public Integer getPostCode(PostCode postCode) {
    return postCode.getPostCode();
  }

  /**
   * Gets the city from a postcode.
   * @param postcode The postcode to get the city from.
   * @return The city of the postcode.
   */
  @Named("getCity")
  public String getCity(PostCode postcode) {
    return postcode.getCity();
  }

  /**
   * Maps a locationResponseDTO to a postcode.
   * @param locationResponseDTO The locationResponseDTO to be mapped to a postcode.
   * @return the postcode in the locationResponseDTO.
   */
  @Mappings({ @Mapping(ignore = true, target = "locations") })
  public abstract PostCode locationResponseDTOTPostCode(
    LocationResponseDTO locationResponseDTO
  );

  /**
   * Maps a LocationCreateDTO to a postcode.
   * @param locationCreateDTO The LocationCreateDTO to be mapped to a postcode.
   * @return the postcode in the LocationCreateDTO.
   */
  @Mappings({ @Mapping(ignore = true, target = "locations") })
  public abstract PostCode locationCreateDTOTPostCode(
    LocationCreateDTO locationCreateDTO
  );

  /**
   * Maps a location to a location response DTO.
   * @param location A location to be mapped to a location response DTO.
   * @return A location response DTO.
   */
  @Mappings(
    {
      @Mapping(
        target = "postCode",
        source = "postCode",
        qualifiedByName = "getPostCode"
      ),
      @Mapping(
        target = "city",
        source = "postCode",
        qualifiedByName = "getCity"
      ),
    }
  )
  public abstract LocationResponseDTO locationToLocationResponseDTO(
    Location location
  );

  /**
   * Maps a location response DTO to a location.
   * @param locationResponseDTO A location response DTO to be mapped to a location.
   * @return A location.
   */
  @Mappings(
    {
      @Mapping(target = "postCode", ignore = true),
      @Mapping(target = "listings", ignore = true),
    }
  )
  public abstract Location locationResponseDTOToLocation(
    LocationResponseDTO locationResponseDTO
  );

  /**
   * Maps a location location create DTO to a location.
   * @param locationCreateDTO A location create DTO to be mapped to a location.
   * @return A location with the values from the location create DTO.
   */
  @Mappings(
    {
      @Mapping(target = "id", ignore = true),
      @Mapping(target = "postCode", ignore = true),
      @Mapping(target = "listings", ignore = true),
    }
  )
  public abstract Location locationCreateDTOToLocation(
    LocationCreateDTO locationCreateDTO
  );
}

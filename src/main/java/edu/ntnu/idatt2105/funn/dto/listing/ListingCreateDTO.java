package edu.ntnu.idatt2105.funn.dto.listing;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Data transfer object for creating listing.
 * Used to transfer listing data between the backend and the application.
 * @author Callum G., Carl G.
 * @version 1.1 - 23.3.2023
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ListingCreateDTO extends ListingCreateUpdateDTO {}

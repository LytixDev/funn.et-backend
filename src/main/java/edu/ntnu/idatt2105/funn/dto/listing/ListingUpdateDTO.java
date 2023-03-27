package edu.ntnu.idatt2105.funn.dto.listing;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Data transfer object for updating listing.
 * Used to transfer listing data between the backend and the application.
 * Contains a list of images which is deleted in backend
 * @author Carl G.
 * @version 1.0 - 27.3.2023
 */
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class ListingUpdateDTO extends ListingCreateDTO {

    private String username;

    private List<Long> imagesToKeep;
}

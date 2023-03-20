package edu.ntnu.idatt2105.placeholder.controller.listing;

import edu.ntnu.idatt2105.placeholder.dto.listing.ListingDTO;
import edu.ntnu.idatt2105.placeholder.service.listing.ListingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller for listing
 * @author Nicolai H. B.
 * @version 1.0 - 18.3.2023
 */
@RestController
@CrossOrigin("*")
public class ListingController {

  private static final Logger LOGGER = LoggerFactory.getLogger(
    ListingController.class
  );

  private final ListingService listingService;

  public ListingController(ListingService listingService) {
    this.listingService = listingService;
  }

  /* temporary and stupid test endpoint */
  @GetMapping("/listing")
  @ResponseBody
  public ResponseEntity<ListingDTO> getListing() {
    ListingDTO listingDTO = new ListingDTO();
    return ResponseEntity.ok(listingDTO);
  }
}

package edu.ntnu.idatt2105.placeholder.model.location;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing a post code id in the system.
 * Used to identify a post code in the system.
 * @author Callum G.
 * @version 1.0 - 17.03.2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostCodeId implements Serializable {

  private String postCode;
  private String city;
}

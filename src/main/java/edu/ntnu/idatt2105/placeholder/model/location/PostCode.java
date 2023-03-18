package edu.ntnu.idatt2105.placeholder.model.location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing a postcode and city in the system.
 * @author Callum G.
 * @version 1.0 - 17.03.2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`postcode_city`")
@IdClass(PostCodeId.class)
public class PostCode {

  @Id
  @Column(name = "`postcode`")
  private String postCode;

  @Id
  @Column(name = "`city`")
  private String city;
}

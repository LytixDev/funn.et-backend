package edu.ntnu.idatt2105.funn.model.location;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Class representing a postcode and city in the system.
 * @author Callum G.
 * @version 1.1 - 21.03.2023
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "`postcode_tbl`")
public class PostCode {

  @Id
  @Column(name = "`postcode`", unique = true, nullable = false)
  @NonNull
  private Integer postCode;

  @Column(name = "`city`", nullable = false)
  @NonNull
  private String city;

  @OneToMany(mappedBy = "postCode", cascade = CascadeType.ALL)
  private Collection<Location> locations;
}

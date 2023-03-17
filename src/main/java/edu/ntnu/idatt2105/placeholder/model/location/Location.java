package edu.ntnu.idatt2105.placeholder.model.location;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing a location in the system.
 * @author Callum G.
 * @version 1.0 - 17.03.2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
  name = "`location`",
  uniqueConstraints = @UniqueConstraint(
    columnNames = { "location_id", "address", "postcode" }
  )
)
public class Location {

  @Id
  @Column(name = "`location_id`")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "`address`")
  private String address;

  @ManyToOne
  @JoinColumn(name = "`postcode`", referencedColumnName = "`postcode`")
  private PostCode postCode;

  @Column(name = "`latitude`")
  private Double latitude;

  @Column(name = "`longitude`")
  private Double longitude;
}

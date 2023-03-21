package edu.ntnu.idatt2105.placeholder.model.location;

import edu.ntnu.idatt2105.placeholder.model.listing.Listing;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing a location in the system.
 * @author Callum G.
 * @version 1.1 - 18.03.2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
  name = "`location`",
  uniqueConstraints = @UniqueConstraint(
    columnNames = { "`location_id`", "`address`", "`postcode`" }
  )
)
public class Location {

  @Id
  @Column(name = "`location_id`")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "`address`", nullable = false)
  private String address;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "`postcode`", referencedColumnName = "`postcode`")
  private PostCode postCode;

  @Column(name = "`latitude`", nullable = false)
  private double latitude;

  @Column(name = "`longitude`", nullable = false)
  private double longitude;

  @OneToMany(
    mappedBy = "location",
    cascade = CascadeType.ALL
  )
  private Collection<Listing> listings;
}

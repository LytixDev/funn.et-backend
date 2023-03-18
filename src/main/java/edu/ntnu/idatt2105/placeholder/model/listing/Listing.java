package edu.ntnu.idatt2105.placeholder.model.listing;

import edu.ntnu.idatt2105.placeholder.model.location.Location;
import edu.ntnu.idatt2105.placeholder.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Entity class for listing.
 * @author Nicolai H. B., Callum G.
 * @version 1.1 - 18.3.2023
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "`listing`")
public class Listing {

  @Id
  @Column(name = "`listing_id`", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "`user`", referencedColumnName = "`username`")
  @NonNull
  private User user;

  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "`location`", referencedColumnName = "`location_id`")
  @NonNull
  private Location location;

  @Column(name = "`title`", length = 64, nullable = false)
  @NonNull
  private String title;

  @Column(name = "`brief_description`", nullable = false)
  @NonNull
  private String briefDescription;

  @Column(name = "`full_description`", length = 512)
  private String fullDescription;

  @Enumerated(EnumType.STRING)
  @Column(name = "`category`", nullable = false)
  private Category category;

  @DecimalMin(value = "0.0")
  @Column(name = "`price`", nullable = false)
  private double price;

  @Column(name = "`publication_date`", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDate publicationDate;

  @Column(name = "`expiration_date`", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private LocalDate expirationDate;

  @Column(name = "`image`")
  private byte[] image;
}

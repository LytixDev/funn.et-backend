package edu.ntnu.idatt2105.funn.model.listing;

import edu.ntnu.idatt2105.funn.model.file.Image;
import edu.ntnu.idatt2105.funn.model.location.Location;
import edu.ntnu.idatt2105.funn.model.user.Chat;
import edu.ntnu.idatt2105.funn.model.user.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Entity class for listing.
 * @author Nicolai H. B., Callum G.
 * @version 1.3 - 23.3.2023
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "`user`", referencedColumnName = "`username`")
  @NonNull
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "`category`", referencedColumnName = "`category_name`")
  @NonNull
  private Category category;

  @DecimalMin(value = "0.0")
  @Column(name = "`price`", nullable = false)
  private double price;

  @Column(name = "`publication_date`", nullable = false)
  private LocalDate publicationDate;

  @Column(name = "`expiration_date`", nullable = false)
  private LocalDate expirationDate;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "`listing`", referencedColumnName = "`listing_id`")
  private List<Image> images;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "`listing`", referencedColumnName = "`listing_id`")
  private List<Chat> chats;

  /**
   * Method for getting the images of a listing.
   * If the list of images is null, a new list is created.
   * @return List of images.
   */
  public List<Image> getImages() {
    if (images == null) {
      images = new ArrayList<>();
    }

    return images;
  }
}

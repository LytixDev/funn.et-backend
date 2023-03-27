package edu.ntnu.idatt2105.funn.model.file;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Class representing an image.
 * @author Callum G.
 * @version 1.0 - 20.03.2023
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "`image_file`")
@Entity
public class Image {

  @Id
  @Column(name = "`image_id`", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "`image_alt`", nullable = false)
  @NonNull
  private String alt;

  @Column(name = "`listing`")
  @NonNull
  private Long listingId;
}

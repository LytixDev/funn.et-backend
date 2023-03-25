package edu.ntnu.idatt2105.funn.model.listing;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

/**
 * Enum representing the category of a listing.
 * @author Thomas H. Svendal
 * @version 2.0 - 24.3.2023
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "`category`")
public class Category {

  @Id
  @Column(name = "`category_id`", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "`category_name`", nullable = false)
  @NonNull
  private String name;

  @OneToMany(mappedBy = "category")
  private Set<Listing> listings;
}

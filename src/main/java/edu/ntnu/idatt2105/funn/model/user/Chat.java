package edu.ntnu.idatt2105.funn.model.user;

import edu.ntnu.idatt2105.funn.model.listing.Listing;
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
 * Class representing a chat.
 * @author Callum G.
 * @version 1.0 - 23.03.2023
 */
@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(
  name = "`chat`",
  uniqueConstraints = @UniqueConstraint(columnNames = { "`listing`", "`messager`" })
)
public class Chat {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "`chat_id`")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "`messager`", referencedColumnName = "`username`")
  private User messager;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "`listing`", referencedColumnName = "`listing_id`")
  private Listing listing;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "`chat`", referencedColumnName = "`chat_id`")
  private Collection<Message> messages;
}

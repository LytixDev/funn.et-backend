package edu.ntnu.idatt2105.funn.model.user;

import edu.ntnu.idatt2105.funn.model.listing.Listing;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Class representing a user in the system.
 * Implements UserDetails to allow Spring Security to use this class for authentication.
 * @author Callum G.
 * @version 1.0
 * @date 13.3.2023
 */
@Setter
@Getter
@AllArgsConstructor
@Builder
@NoArgsConstructor
@Entity
@Table(name = "`user`")
public class User implements UserDetails {

  @Id
  @Column(name = "`username`", length = 64, nullable = false)
  @NonNull
  private String username;

  @Column(name = "`email`", unique = true, length = 64, nullable = false)
  @NonNull
  private String email;

  @Column(name = "`first_name`", length = 64, nullable = false)
  @NonNull
  private String firstName;

  @Column(name = "`last_name`", length = 64, nullable = false)
  @NonNull
  private String lastName;

  @Column(name = "`password`", nullable = false)
  @NonNull
  private String password;

  @OneToMany(mappedBy = "user", orphanRemoval = true, cascade = CascadeType.ALL)
  private Collection<Listing> listings;

  @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  @JoinTable(
    name = "listing_favorite",
    joinColumns = @JoinColumn(name = "username"),
    inverseJoinColumns = @JoinColumn(name = "listing_id")
  )
  private Set<Listing> favoriteListings;

  @Enumerated(EnumType.STRING)
  @Column(name = "`role`", nullable = false)
  @NonNull
  private Role role;

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(role.name()));
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}

package edu.ntnu.idatt2105.funn.model.user;

import edu.ntnu.idatt2105.funn.model.listing.Listing;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Collection;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
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
@Data
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

  @OneToMany(mappedBy = "messager", orphanRemoval = true, cascade = CascadeType.ALL)
  private Collection<Chat> chats;

  @OneToMany(mappedBy = "sender", orphanRemoval = true, cascade = CascadeType.ALL)
  private Collection<Message> messages;

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

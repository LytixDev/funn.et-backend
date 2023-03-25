package edu.ntnu.idatt2105.funn.repository.user;

import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.model.user.Chat;
import edu.ntnu.idatt2105.funn.model.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Interface for the repository class for the chat repository.
 * @author Callum G.
 * @version 1.0 - 23.3.2023
 */
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
  List<Chat> findChatsByMessager(String username);

  @Query("SELECT c FROM Chat c JOIN c.messages m WHERE c.messager = ?1 OR c.listing.user = ?1 ORDER BY m.timestamp DESC")
  List<Chat> findChatsByUser(User user);

  List<Chat> findChatsByListing(Listing listing);

  Optional<Chat> findChatByListingAndMessager(Listing listing, User messager);
}

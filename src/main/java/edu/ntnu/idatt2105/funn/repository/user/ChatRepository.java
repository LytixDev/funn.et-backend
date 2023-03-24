package edu.ntnu.idatt2105.funn.repository.user;

import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.model.user.Chat;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface for the repository class for the chat repository.
 * @author Callum G.
 * @version 1.0 - 23.3.2023
 */
@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
  List<Chat> findChatsByMessager(String username);

  List<Chat> findChatsByListing(Listing listing);
}

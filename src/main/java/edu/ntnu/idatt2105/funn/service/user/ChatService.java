package edu.ntnu.idatt2105.funn.service.user;
import org.springframework.stereotype.Service;

import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.model.user.Chat;
import edu.ntnu.idatt2105.funn.model.user.User;

/**
 * Interface for the service class for the chat service.
 * @author Callum G.
 * @version 1.0 - 23.3.2023
 */
@Service
public interface ChatService {
    Chat createChat(User user, Listing listing);

    Chat getChat(User user, Long id);

    void sendMessage(User user, Chat chat, String message);

    void deleteChat(User user, Chat chat);
}

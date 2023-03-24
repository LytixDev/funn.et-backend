package edu.ntnu.idatt2105.funn.service.user;

import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.model.user.Chat;
import edu.ntnu.idatt2105.funn.model.user.Message;
import edu.ntnu.idatt2105.funn.model.user.User;
import edu.ntnu.idatt2105.funn.repository.user.ChatRepository;
import java.sql.Timestamp;
import java.util.HashSet;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service class for the chat service.
 * @author Callum G.
 * @version 1.0 - 23.3.2023
 */
@Service
public class ChatServiceImpl implements ChatService {

  @Autowired
  private ChatRepository chatRepository;

  /**
   * Creates a chat between a user and a listing.
   * @param user The user creating the chat.
   * @param listing The listing the chat is for.
   * @return The created chat.
   * @throws NullPointerException If the user is null.
   * @throws NullPointerException If the listing is null.
   */
  @Override
  public Chat createChat(@NonNull User user, @NonNull Listing listing) {
    if (
      chatRepository
        .findChatsByMessager(user.getUsername())
        .stream()
        .anyMatch(chat -> chat.getListing().getId() == listing.getId())
    ) throw new IllegalArgumentException("Chat already exists");

    if (
      user.getUsername().equals(listing.getUser().getUsername())
    ) throw new IllegalArgumentException("Cannot create chat with yourself");

    return chatRepository.save(
      Chat.builder().messager(user).listing(listing).messages(new HashSet<>()).build()
    );
  }

  /**
   * Gets a chat by id.
   * @param user The user getting the chat.
   * @param id The id of the chat.
   * @return The chat.
   */
  @Override
  public Chat getChat(@NonNull User user, @NonNull Long id) {
    Chat chat = chatRepository
      .findById(id)
      .orElseThrow(() -> new IllegalArgumentException("Chat does not exist"));

    if (
      !chat.getMessager().getUsername().equals(user.getUsername()) ||
      !chat.getListing().getUser().getUsername().equals(user.getUsername())
    ) throw new IllegalArgumentException("Cannot get chat you are not a part of");

    return chat;
  }

  /**
   * Sends a message to a chat.
   * @param user The user sending the message.
   * @param chat The chat the message is being sent to.
   * @param message The message being sent.
   * @throws NullPointerException If the chat is null.
   * @throws NullPointerException If the user is null.
   * @throws NullPointerException If the message is null.
   */
  @Override
  public void sendMessage(@NonNull User user, @NonNull Chat chat, @NonNull String message) {
    if (
      !chat.getMessager().getUsername().equals(user.getUsername()) ||
      !chat.getListing().getUser().getUsername().equals(user.getUsername())
    ) throw new IllegalArgumentException("Cannot send message to chat you are not a part of");

    chat
      .getMessages()
      .add(
        Message
          .builder()
          .chat(chat)
          .message(message)
          .sender(user)
          .timestamp(new Timestamp(System.currentTimeMillis()))
          .build()
      );
    chatRepository.save(chat);
  }

  /**
   * Deletes a chat.
   * @param user The user deleting the chat.
   * @param chat The chat being deleted.
   * @throws NullPointerException If the chat is null.
   * @throws NullPointerException If the user is null.
   */
  @Override
  public void deleteChat(@NonNull User user, @NonNull Chat chat) {
    if (
      !chat.getMessager().getUsername().equals(user.getUsername()) ||
      !chat.getListing().getUser().getUsername().equals(user.getUsername())
    ) throw new IllegalArgumentException("Cannot delete chat you are not a part of");

    chatRepository.delete(chat);
  }
}

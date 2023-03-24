package edu.ntnu.idatt2105.funn.controller.user;

import edu.ntnu.idatt2105.funn.dto.user.ChatDTO;
import edu.ntnu.idatt2105.funn.dto.user.MessageDTO;
import edu.ntnu.idatt2105.funn.exceptions.listing.ListingNotFoundException;
import edu.ntnu.idatt2105.funn.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.funn.mapper.user.MessageMapper;
import edu.ntnu.idatt2105.funn.mapper.user.UserMapper;
import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.model.user.Chat;
import edu.ntnu.idatt2105.funn.model.user.Message;
import edu.ntnu.idatt2105.funn.model.user.User;
import edu.ntnu.idatt2105.funn.service.listing.ListingService;
import edu.ntnu.idatt2105.funn.service.user.ChatService;
import edu.ntnu.idatt2105.funn.service.user.UserService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller for the chat service.
 * @author Callum G.
 * @version 1.0 - 23.3.2023
 */
@RestController
@RequestMapping(value = "/api/v1/private")
@EnableAutoConfiguration
@RequiredArgsConstructor
public class ChatController {

  private final ChatService chatService;

  private final ListingService listingService;

  private final UserService userService;

  private static final Logger LOGGER = LoggerFactory.getLogger(ChatController.class);

  /**
   * Create a chat between a user and a listing.
   * @param id The id of the listing.
   * @param username The username of the user.
   * @return The created chat.
   */
  @PostMapping(value = "/listing/{id}/chat", produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<ChatDTO> createChat(
    @PathVariable("id") Long id,
    @AuthenticationPrincipal String username
  ) throws ListingNotFoundException, UserDoesNotExistsException, NullPointerException {
    LOGGER.info("Creating chat between user {} and listing {}", username, id);

    Listing listing = null;

    try {
      listing = listingService.getListing(id);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw e;
    }

    LOGGER.info("Listing found: {}", listing);

    LOGGER.info("Getting user {}", username);

    User user = null;

    try {
      user = userService.getUserByUsername(username);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw e;
    }

    LOGGER.info("User found: {}", user);

    LOGGER.info("Creating chat");

    Chat chat = null;

    try {
      chat = chatService.createChat(user, listing);
    } catch (Exception e) {
      LOGGER.error(e.getClass() + e.getMessage());
      throw e;
    }

    ChatDTO chatDTO = new ChatDTO(
      chat.getId(),
      UserMapper.INSTANCE.userToUserDTO(chat.getMessager()),
      UserMapper.INSTANCE.userToUserDTO(listing.getUser()),
      chat.getListing().getId(),
      chat
        .getMessages()
        .stream()
        .map(MessageMapper.INSTANCE::messageToMessageDTO)
        .collect(Collectors.toList())
    );

    return ResponseEntity.status(HttpStatus.CREATED).body(chatDTO);
  }

  /**
   * Get messages from a chat.
   * @param chatId The id of the chat.
   * @param username The username of the user.
   * @return The chat.
   */
  @GetMapping(value = "/chat/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<ChatDTO> getChat(
    @PathVariable("id") Long chatId,
    @AuthenticationPrincipal String username
  ) throws UserDoesNotExistsException, NullPointerException {
    LOGGER.info("Getting chat {}", chatId);

    User user = userService.getUserByUsername(username);

    LOGGER.info("User found: {}", user);

    LOGGER.info("Getting chat");
    Chat chat = chatService.getChat(user, chatId);

    ChatDTO chatDTO = new ChatDTO(
      chat.getId(),
      UserMapper.INSTANCE.userToUserDTO(chat.getMessager()),
      UserMapper.INSTANCE.userToUserDTO(chat.getListing().getUser()),
      chat.getListing().getId(),
      chat
        .getMessages()
        .stream()
        .map(MessageMapper.INSTANCE::messageToMessageDTO)
        .collect(Collectors.toList())
    );

    return ResponseEntity.status(HttpStatus.OK).body(chatDTO);
  }

  /**
   * Send a message to a chat.
   * @param chat The chat to send the message to.
   * @param message The message to send.
   */
  @PostMapping(
    value = "/chat/{id}",
    consumes = { MediaType.APPLICATION_JSON_VALUE },
    produces = { MediaType.APPLICATION_JSON_VALUE }
  )
  public ResponseEntity<MessageDTO> sendMessage(
    @PathVariable("id") Long chatId,
    @AuthenticationPrincipal String username,
    @RequestBody MessageDTO messageDTO
  ) throws UserDoesNotExistsException, NullPointerException {
    User sender = userService.getUserByUsername(username);

    LOGGER.info("Message to send: {}", messageDTO.getMessage());

    Chat chat = chatService.getChat(sender, chatId);

    LOGGER.info("Sending message to chat {}", chatId);

    Message message = chatService.sendMessage(sender, chat, messageDTO.getMessage());

    messageDTO = MessageMapper.INSTANCE.messageToMessageDTO(message);

    return ResponseEntity.status(HttpStatus.CREATED).body(messageDTO);
  }
}

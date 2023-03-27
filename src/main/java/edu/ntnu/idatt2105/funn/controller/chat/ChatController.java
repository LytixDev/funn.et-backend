package edu.ntnu.idatt2105.funn.controller.chat;

import edu.ntnu.idatt2105.funn.dto.user.ChatDTO;
import edu.ntnu.idatt2105.funn.dto.user.MessageDTO;
import edu.ntnu.idatt2105.funn.exceptions.PermissionDeniedException;
import edu.ntnu.idatt2105.funn.exceptions.listing.ListingNotFoundException;
import edu.ntnu.idatt2105.funn.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.funn.exceptions.validation.BadInputException;
import edu.ntnu.idatt2105.funn.mapper.user.MessageMapper;
import edu.ntnu.idatt2105.funn.mapper.user.UserMapper;
import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.model.user.Chat;
import edu.ntnu.idatt2105.funn.model.user.Message;
import edu.ntnu.idatt2105.funn.model.user.User;
import edu.ntnu.idatt2105.funn.security.Auth;
import edu.ntnu.idatt2105.funn.service.listing.ListingService;
import edu.ntnu.idatt2105.funn.service.user.ChatService;
import edu.ntnu.idatt2105.funn.service.user.UserService;
import edu.ntnu.idatt2105.funn.validation.AuthValidation;
import edu.ntnu.idatt2105.funn.validation.ChatValidation;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
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
   * @param auth The auth of the user that wants to create the chat.
   * @return The created chat as a ChatDTO.
   * @throws ListingNotFoundException If the listing is not found.
   * @throws PermissionDeniedException If the user is the listing owner or the auth is null.
   * @throws UserDoesNotExistsException If the user is not found.
   * @throws NullPointerException If the auth is null.
   */
  @PostMapping(value = "/listings/{id}/chat", produces = { MediaType.APPLICATION_JSON_VALUE })
  @Operation(
    summary = "Create a chat between a user and a listing.",
    description = "Create a chat between a user and a listing. The chat will be between the user and the listing owner. The user cannot be the listing owner."
  )
  public ResponseEntity<ChatDTO> createChat(
    @PathVariable("id") Long id,
    @AuthenticationPrincipal Auth auth
  )
    throws ListingNotFoundException, PermissionDeniedException, UserDoesNotExistsException, NullPointerException {
    Listing listing = listingService.getListing(id);

    if (
      !AuthValidation.isNotUser(auth, listing.getUser().getUsername())
    ) throw new PermissionDeniedException("Auth is null.");

    final String username = auth.getUsername();

    LOGGER.info(
      "Creating chat between messager: {} and owner: {} on listing id: {}",
      username,
      listing.getUser().getUsername(),
      listing.getId()
    );

    User user = userService.getUserByUsername(username);

    LOGGER.info("User found: {}", user);

    LOGGER.info("Creating chat");

    Chat chat = chatService.createChat(user, listing);

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

    LOGGER.info("Chat made: {}", chatDTO);

    return ResponseEntity.status(HttpStatus.CREATED).body(chatDTO);
  }

  /**
   * Get messages from a chat.
   * @param chatId The id of the chat.
   * @param auth the authentication object of the user that is getting the chat.
   * @return The chat as a ChatDTO.
   * @throws PermissionDeniedException If the user is not in the chat or the auth is null.
   * @throws NullPointerException If the auth is null.
   */
  @GetMapping(value = "/chat/{id}", produces = { MediaType.APPLICATION_JSON_VALUE })
  @Operation(summary = "Get messages from a chat.", description = "Get messages from a chat.")
  public ResponseEntity<ChatDTO> getChat(
    @PathVariable("id") Long chatId,
    @AuthenticationPrincipal Auth auth
  ) throws PermissionDeniedException, NullPointerException {
    if (!AuthValidation.validateAuth(auth)) throw new PermissionDeniedException(
      "Not authorized for this chat."
    );

    LOGGER.info("Getting chat with id: {}", chatId);

    Chat chat = chatService.getChat(chatId);

    if (!ChatValidation.isUserInChat(auth.getUsername(), chat)) throw new PermissionDeniedException(
      "Not authorized for this chat."
    );

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
   * Gets chat by listing and username.
   * @param listingId The id of the listing.
   * @param auth the authentication object of the user that is getting the chat.
   * @return The chat.
   * @throws PermissionDeniedException If the user is not in the chat or the auth is null.
   * @throws UserDoesNotExistsException If the user is not found.
   * @throws NullPointerException If the auth is null.
   * @throws PermissionDeniedException If the user is not in the chat.
   */
  @GetMapping(
    value = "/listings/{id}/chat/{username}",
    produces = { MediaType.APPLICATION_JSON_VALUE }
  )
  @Operation(
    summary = "Gets chat by listing and username.",
    description = "Gets chat by listing and username. The user must be in the chat, either as the messager or the listing owner."
  )
  public ResponseEntity<ChatDTO> getChatByListingAndUser(
    @PathVariable("id") Long listingId,
    @PathVariable("username") String pathUsername,
    @AuthenticationPrincipal Auth auth
  )
    throws PermissionDeniedException, UserDoesNotExistsException, NullPointerException, PermissionDeniedException {
    if (!AuthValidation.validateAuth(auth)) throw new PermissionDeniedException(
      "Not authorized for this chat."
    );

    LOGGER.info("Getting chat between listing {} and user {}", listingId, pathUsername);

    Listing listing = listingService.getListing(listingId);

    if (
      !ChatValidation.isUserInChat(pathUsername, pathUsername, listing.getUser().getUsername())
    ) throw new PermissionDeniedException("Not authorized for this chat.");

    LOGGER.info("Getting chat");
    Chat chat = chatService.getChat(userService.getUserByUsername(pathUsername), listing);

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

    LOGGER.info("Chat found: {}", chatDTO);

    return ResponseEntity.status(HttpStatus.OK).body(chatDTO);
  }

  /**
   * Get all chats for a user.
   * @param auth the authentication object of the user that is getting the chats.
   * @return The chats as a list of ChatDTOs.
   * @throws PermissionDeniedException If the auth is null.
   * @throws UserDoesNotExistsException If the user is not found.
   */
  @GetMapping(value = "/chat", produces = { MediaType.APPLICATION_JSON_VALUE })
  @Operation(summary = "Get all chats for a user.", description = "Get all chats for a user.")
  public ResponseEntity<List<ChatDTO>> getChats(@AuthenticationPrincipal Auth auth)
    throws PermissionDeniedException, UserDoesNotExistsException {
    if (!AuthValidation.validateAuth(auth)) throw new PermissionDeniedException("Not authorized.");

    final String username = auth.getUsername();

    LOGGER.info("Getting chats for user {}", username);

    User user = userService.getUserByUsername(username);

    LOGGER.info("User found: {}", user);

    LOGGER.info("Getting chats");
    List<Chat> chats = chatService.getChats(user);

    List<ChatDTO> chatDTOs = chats
      .stream()
      .map(chat ->
        new ChatDTO(
          chat.getId(),
          UserMapper.INSTANCE.userToUserDTO(chat.getMessager()),
          UserMapper.INSTANCE.userToUserDTO(chat.getListing().getUser()),
          chat.getListing().getId(),
          chat
            .getMessages()
            .stream()
            .map(MessageMapper.INSTANCE::messageToMessageDTO)
            .collect(Collectors.toList())
        )
      )
      .collect(Collectors.toList());

    LOGGER.info("Chats found: {}", chatDTOs);

    return ResponseEntity.status(HttpStatus.OK).body(chatDTOs);
  }

  /**
   * Send a message to a chat.
   * @param chat The chat to send the message to.
   * @param message The message to send.
   * @return The message that was sent as a MessageDTO.
   * @throws PermissionDeniedException If the user is not in the chat or the auth is null.
   * @throws UserDoesNotExistsException If the user is not found.
   * @throws BadInputException If the message is not valid.
   * @throws NullPointerException If the auth is null.
   */
  @PostMapping(
    value = "/chat/{id}",
    consumes = { MediaType.APPLICATION_JSON_VALUE },
    produces = { MediaType.APPLICATION_JSON_VALUE }
  )
  public ResponseEntity<MessageDTO> sendMessage(
    @PathVariable("id") Long chatId,
    @AuthenticationPrincipal Auth auth,
    @RequestBody MessageDTO messageDTO
  )
    throws PermissionDeniedException, UserDoesNotExistsException, BadInputException, NullPointerException {
    if (!AuthValidation.validateAuth(auth)) throw new PermissionDeniedException(
      "Not authorized for this chat."
    );

    if (!ChatValidation.validateMessage(messageDTO.getMessage())) throw new BadInputException(
      "Message has bad input."
    );

    LOGGER.info("Message to send: {}", messageDTO.getMessage());

    Chat chat = chatService.getChat(chatId);

    if (!ChatValidation.isUserInChat(auth.getUsername(), chat)) throw new PermissionDeniedException(
      "Not authorized for this chat."
    );

    User sender = userService.getUserByUsername(auth.getUsername());

    LOGGER.info("Sending message to chat {}", chatId);

    Message message = chatService.sendMessage(sender, chat, messageDTO.getMessage());

    messageDTO = MessageMapper.INSTANCE.messageToMessageDTO(message);

    return ResponseEntity.status(HttpStatus.CREATED).body(messageDTO);
  }
}

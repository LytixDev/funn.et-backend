package edu.ntnu.idatt2105.funn.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.ntnu.idatt2105.funn.dto.user.MessageDTO;
import edu.ntnu.idatt2105.funn.exceptions.listing.ListingNotFoundException;
import edu.ntnu.idatt2105.funn.exceptions.user.UserDoesNotExistsException;
import edu.ntnu.idatt2105.funn.mapper.user.MessageMapper;
import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.model.user.Chat;
import edu.ntnu.idatt2105.funn.model.user.Message;
import edu.ntnu.idatt2105.funn.model.user.User;
import edu.ntnu.idatt2105.funn.service.listing.ListingService;
import edu.ntnu.idatt2105.funn.service.user.ChatService;
import edu.ntnu.idatt2105.funn.service.user.UserService;
import lombok.RequiredArgsConstructor;

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
    @PostMapping(value = "/listing/{id}/chat/{username}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Chat> createChat(@PathVariable("id") Long id, @PathVariable("username") String username) throws ListingNotFoundException, UserDoesNotExistsException, NullPointerException {
        LOGGER.info("Creating chat between user {} and listing {}", username, id);
        
        Listing listing = listingService.getListing(id);

        LOGGER.info("Listing found: {}", listing);

        User user = userService.getUserByUsername(username);
        
        LOGGER.info("User found: {}", user);

        LOGGER.info("Creating chat");
        Chat chat = chatService.createChat(user, listing);

        return ResponseEntity.status(HttpStatus.CREATED).body(chat);
    }

    /**
     * Get messages from a chat.
     * @param chatId The id of the chat.
     * @param username The username of the user.
     * @return The chat.
     */
    @GetMapping(value = "/chat/{id}/", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Chat> getChat(@PathVariable("id") Long chatId, @PathVariable("username") String username) throws UserDoesNotExistsException, NullPointerException {
        LOGGER.info("Getting chat {}", chatId);

        User user = userService.getUserByUsername(username);

        LOGGER.info("User found: {}", user);

        LOGGER.info("Getting chat");
        Chat chat = chatService.getChat(user, chatId);

        return ResponseEntity.status(HttpStatus.OK).body(chat);
    }

    /**
     * Send a message to a chat.
     * @param chat The chat to send the message to.
     * @param message The message to send.
     */
    @PostMapping(value = "/chat/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Message> sendMessage(@PathVariable("id") Long chatId, @RequestBody MessageDTO messageDTO) throws UserDoesNotExistsException, NullPointerException {
        Message message = MessageMapper.INSTANCE.messageDTOToMessage(messageDTO);
        
        User sender = userService.getUserByUsername(messageDTO.getSender().getUsername());

        message.setSender(sender);

        LOGGER.info("Message to send: {}", message.getMessage());

        Chat chat = chatService.getChat(sender, chatId);

        message.setChat(chat);

        LOGGER.info("Sending message to chat {}", chatId);

        chatService.sendMessage(message.getSender(), message.getChat(), message.getMessage());

        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }
}

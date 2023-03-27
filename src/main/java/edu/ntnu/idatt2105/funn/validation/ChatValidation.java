package edu.ntnu.idatt2105.funn.validation;

import edu.ntnu.idatt2105.funn.model.user.Chat;

/**
 * Class for chat validation.
 * Used for validation of user input.
 * @author Callum G.
 * @version 1.0 - 27.03.2023
 */
public class ChatValidation extends BaseValidation {

  /**
   * Validate a message.
   * @param message The message to validate.
   * @return True if the message is valid, false otherwise.
   */
  public static boolean validateMessage(String message) {
    return isNotNullOrEmpty(message) && isSmallerThan(message, 255);
  }

  /**
   * Validate if a user is in a chat.
   * @param username The username to validate.
   * @param chat The chat to validate.
   * @return True if the user is in the chat, false otherwise.
   */
  public static boolean isUserInChat(String username, Chat chat) {
    if (isNotNullOrEmpty(username) && chat != null) return (
      chat.getMessager().getUsername().equals(username) ||
      chat.getListing().getUser().getUsername().equals(username)
    );

    return false;
  }

  /**
   * Validate if a user is in a chat.
   * @param username The user to validate.
   * @param chatMessager The chat messager to validate.
   * @param chatListingUser The chat listing user to validate.
   * @return True if the user is in the chat, false otherwise.
   */
  public static boolean isUserInChat(String username, String chatMessager, String chatListingUser) {
    if (
      isNotNullOrEmpty(username) &&
      isNotNullOrEmpty(chatMessager) &&
      isNotNullOrEmpty(chatListingUser)
    ) return chatMessager.equals(username) || chatListingUser.equals(username);

    return false;
  }
}

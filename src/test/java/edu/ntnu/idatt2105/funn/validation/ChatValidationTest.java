package edu.ntnu.idatt2105.funn.validation;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import edu.ntnu.idatt2105.funn.model.listing.Listing;
import edu.ntnu.idatt2105.funn.model.user.Chat;
import edu.ntnu.idatt2105.funn.model.user.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class ChatValidationTest {

  @Mock
  private Chat mockChat;

  @Mock
  private User mockUser;

  @Mock
  private User mockUser2;

  @Mock
  private Listing mockListing;

  private final String goodName = "goodName";
  private final String ownerName = "ownerName";
  private final String badName = "badName";

  private final String goodMessage = "goodMessage";
  private final String longMessage =
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage" +
    "messagemessagemessage";

  private final String nullMessage = null;
  private final String emptyMessage = "";

  @Before
  public void initService() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  public void testValidateNameReturnsTrueOnGoodMessage() {
    assertTrue(ChatValidation.validateMessage(goodMessage));
  }

  @Test
  public void testValidateNameReturnsFalseOnLongMessage() {
    assertFalse(ChatValidation.validateMessage(longMessage));
  }

  @Test
  public void testValidateNameReturnsFalseOnNullMessage() {
    assertFalse(ChatValidation.validateMessage(nullMessage));
  }

  @Test
  public void testValidateNameReturnsFalseOnEmptyMessage() {
    assertFalse(ChatValidation.validateMessage(emptyMessage));
  }

  @Test
  public void testValidateNameReturnsTrueOnGoodName() {
    when(mockChat.getListing()).thenReturn(mockListing);
    when(mockListing.getUser()).thenReturn(mockUser);
    when(mockUser.getUsername()).thenReturn(ownerName);

    when(mockChat.getMessager()).thenReturn(mockUser2);
    when(mockUser2.getUsername()).thenReturn(goodName);

    assertTrue(ChatValidation.isUserInChat(goodName, mockChat));
  }

  @Test
  public void testValidateNameReturnsFalseOnBadName() {
    when(mockChat.getListing()).thenReturn(mockListing);
    when(mockListing.getUser()).thenReturn(mockUser);
    when(mockUser.getUsername()).thenReturn(ownerName);

    when(mockChat.getMessager()).thenReturn(mockUser2);
    when(mockUser2.getUsername()).thenReturn(goodName);

    assertFalse(ChatValidation.isUserInChat(badName, mockChat));
  }

  @Test
  public void testValidateNameReturnsFalseOnNullName() {
    when(mockChat.getListing()).thenReturn(mockListing);
    when(mockListing.getUser()).thenReturn(mockUser);
    when(mockUser.getUsername()).thenReturn(ownerName);

    when(mockChat.getMessager()).thenReturn(mockUser2);
    when(mockUser2.getUsername()).thenReturn(goodName);

    assertFalse(ChatValidation.isUserInChat(null, mockChat));
  }

  @Test
  public void testValidateNameReturnsFalseOnEmptyName() {
    when(mockChat.getListing()).thenReturn(mockListing);
    when(mockListing.getUser()).thenReturn(mockUser);
    when(mockUser.getUsername()).thenReturn(ownerName);

    when(mockChat.getMessager()).thenReturn(mockUser2);
    when(mockUser2.getUsername()).thenReturn(goodName);

    assertFalse(ChatValidation.isUserInChat("", mockChat));
  }

  @Test
  public void testValidateNameReturnsTrueOnOwnerName() {
    assertTrue(ChatValidation.isUserInChat(goodName, goodName, ownerName));
  }

  @Test
  public void testValidateNameReturnsFalseOnBadOwnerName() {
    assertFalse(ChatValidation.isUserInChat(badName, goodName, ownerName));
  }

  @Test
  public void testValidateNameReturnsFalseOnNullOwnerName() {
    assertFalse(ChatValidation.isUserInChat(null, goodName, ownerName));
  }

  @Test
  public void testValidateNameReturnsFalseOnEmptyOwnerName() {
    assertFalse(ChatValidation.isUserInChat("", goodName, ownerName));
  }
}

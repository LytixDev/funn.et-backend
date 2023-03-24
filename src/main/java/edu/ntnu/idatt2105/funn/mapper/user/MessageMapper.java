package edu.ntnu.idatt2105.funn.mapper.user;

import edu.ntnu.idatt2105.funn.dto.user.MessageDTO;
import edu.ntnu.idatt2105.funn.model.user.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * Class used to map between Message and MessageDTO.
 * @author Callum G.
 * @version 1.0 - 23.03.2023
 */
@Mapper(componentModel = "spring")
public interface MessageMapper {
  MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

  /**
   * Maps a message DTO to a message.
   * @param messageDTO A message in the form of a DTO
   * @return A message object.
   */
  @Mapping(target = "sender", ignore = true)
  Message messageDTOToMessage(MessageDTO messageDTO);

  /**
   * Maps a message to a message DTO.
   * @param message A message object.
   * @return A message in the form of a DTO.
   */
  MessageDTO messageToMessageDTO(Message message);
}

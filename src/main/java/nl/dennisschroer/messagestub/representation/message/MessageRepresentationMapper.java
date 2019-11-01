package nl.dennisschroer.messagestub.representation.message;

import nl.dennisschroer.messagestub.message.Message;

import java.util.List;

/**
 * @author Dennis Schroer
 * @since 01 Nov 2019
 */
public interface MessageRepresentationMapper {
    MessageRepresentation toRepresentation(Message message);

    MessagesRepresentation toRepresentation(List<Message> messages);
}

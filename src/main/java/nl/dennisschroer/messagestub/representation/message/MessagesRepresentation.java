package nl.dennisschroer.messagestub.representation.message;

import org.springframework.hateoas.CollectionModel;

import java.util.List;

/**
 * @author Dennis Schroer
 * @since 01 Nov 2019
 */
public class MessagesRepresentation extends CollectionModel<MessageRepresentation> {
    public MessagesRepresentation(List<MessageRepresentation> messages) {
        super(messages);
    }
}

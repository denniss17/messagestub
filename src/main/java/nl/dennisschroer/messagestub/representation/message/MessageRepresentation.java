package nl.dennisschroer.messagestub.representation.message;

import nl.dennisschroer.messagestub.controller.ExchangeMessageController;
import nl.dennisschroer.messagestub.controller.MessageController;
import nl.dennisschroer.messagestub.message.Message;
import org.springframework.hateoas.EntityModel;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Dennis Schroer
 * @since 01 Nov 2019
 */
public class MessageRepresentation extends EntityModel<Message> {
    public MessageRepresentation(Message message) {
        super(message);


    }
}

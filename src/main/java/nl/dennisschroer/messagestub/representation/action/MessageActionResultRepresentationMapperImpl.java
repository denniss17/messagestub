package nl.dennisschroer.messagestub.representation.action;

import nl.dennisschroer.messagestub.controller.ExchangeMessageController;
import nl.dennisschroer.messagestub.controller.MessageController;
import nl.dennisschroer.messagestub.exchange.ExchangeMessage;
import nl.dennisschroer.messagestub.message.Message;
import nl.dennisschroer.messagestub.message.action.MessageActionResult;
import org.springframework.stereotype.Service;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Dennis Schroer
 * @since 01 Nov 2019
 */
@Service
public class MessageActionResultRepresentationMapperImpl implements MessageActionResultRepresentationMapper {
    @Override
    public MessageActionResultRepresentation toRepresentation(MessageActionResult messageActionResult) {
        MessageActionResultRepresentation representation = new MessageActionResultRepresentation();
        representation.setError(messageActionResult.getError());
        representation.setSuccess(messageActionResult.isSuccess());

        messageActionResult.getGeneratedEntities().forEach(generatedEntity -> {
            if (generatedEntity instanceof Message) {
                representation.add(linkTo(methodOn(MessageController.class).getMessage(((Message) generatedEntity).getId())).withRel("generatedEntity"));
            }

            if (generatedEntity instanceof ExchangeMessage) {
                representation.add(linkTo(methodOn(ExchangeMessageController.class).getExchangeMessage(((ExchangeMessage) generatedEntity).getId())).withRel("generatedEntity"));
            }
        });

        return representation;
    }
}

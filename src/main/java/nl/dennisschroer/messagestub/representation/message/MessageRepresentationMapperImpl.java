package nl.dennisschroer.messagestub.representation.message;

import nl.dennisschroer.messagestub.controller.ExchangeMessageController;
import nl.dennisschroer.messagestub.controller.MessageController;
import nl.dennisschroer.messagestub.message.Message;
import nl.dennisschroer.messagestub.message.action.MessageActionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Dennis Schroer
 * @since 01 Nov 2019
 */
@Service
public class MessageRepresentationMapperImpl implements MessageRepresentationMapper {

    private final MessageActionService messageActionService;

    public MessageRepresentationMapperImpl(MessageActionService messageActionService) {
        this.messageActionService = messageActionService;
    }

    @Override
    public MessageRepresentation toRepresentation(Message message) {
        MessageRepresentation representation = new MessageRepresentation(message);

        // Ref to self
        representation.add(linkTo(methodOn(MessageController.class).getMessage(message.getId())).withSelfRel());

        // Refs to exchangemessages
        if (message.getExchangeMessages() != null) {
            message.getExchangeMessages().forEach(exchangeMessage -> {
                representation.add(linkTo(methodOn(ExchangeMessageController.class).getExchangeMessage(exchangeMessage.getId()))
                        .withRel("exchangeMessage")
                        .withTitle(exchangeMessage.getMessageType() + " via " + exchangeMessage.getExchangeType())
                );
            });
        }

        // Refs to actions
        messageActionService.getActionsForMessageType(message.getType()).forEach(messageAction -> {
            representation.add(linkTo(methodOn(MessageController.class).executeAction(message.getId(), messageAction.getName()))
                    .withRel(messageAction.getName())
                    .withTitle(messageAction.getDescription())
                    .withType("action")
            );
        });

        return representation;
    }

    @Override
    public MessagesRepresentation toRepresentation(List<Message> messages) {
        MessagesRepresentation representation = new MessagesRepresentation(messages.stream().map(this::toRepresentation).collect(Collectors.toList()));
        representation.add(linkTo(methodOn(ExchangeMessageController.class).getExchangeMessages()).withSelfRel());
        return representation;
    }
}

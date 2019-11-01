package nl.dennisschroer.messagestub.representation.exchangemessage;

import nl.dennisschroer.messagestub.controller.ExchangeMessageController;
import nl.dennisschroer.messagestub.controller.MessageController;
import nl.dennisschroer.messagestub.exchange.ExchangeMessage;
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
public class ExchangeMessageRepresentationMapperImpl implements ExchangeMessageRepresentationMapper {

    private final MessageActionService messageActionService;

    public ExchangeMessageRepresentationMapperImpl(MessageActionService messageActionService) {
        this.messageActionService = messageActionService;
    }

    @Override
    public ExchangeMessageRepresentation toRepresentation(ExchangeMessage exchangeMessage) {
        ExchangeMessageRepresentation representation = new ExchangeMessageRepresentation(exchangeMessage);

        // Ref to self
        representation.add(linkTo(methodOn(ExchangeMessageController.class).getExchangeMessage(exchangeMessage.getId())).withSelfRel());

        // Ref to message
        if (exchangeMessage.getMessage() != null) {
            representation.add(linkTo(methodOn(MessageController.class).getMessage(exchangeMessage.getMessage().getId())).withRel("message"));
        }

        // Ref to response
        if (exchangeMessage.getResponseMessage() != null) {
            representation.add(linkTo(methodOn(ExchangeMessageController.class).getExchangeMessage(exchangeMessage.getResponseMessage().getId())).withRel("response"));
        }

        // Refs to actions
        messageActionService.getActionsForExchangeMessageType(exchangeMessage.getMessageType()).forEach(messageAction -> {
            representation.add(linkTo(methodOn(ExchangeMessageController.class).executeAction(exchangeMessage.getId(), messageAction.getName()))
                    .withRel(messageAction.getName())
                    .withTitle(messageAction.getDescription())
                    .withType("action")
            );
        });

        return representation;
    }

    @Override
    public ExchangeMessagesRepresentation toRepresentation(List<ExchangeMessage> exchangeMessages) {
        ExchangeMessagesRepresentation representation = new ExchangeMessagesRepresentation(exchangeMessages.stream().map(this::toRepresentation).collect(Collectors.toList()));
        representation.add(linkTo(methodOn(ExchangeMessageController.class).getExchangeMessages()).withSelfRel());
        return representation;
    }
}

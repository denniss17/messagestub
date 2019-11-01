package nl.dennisschroer.messagestub.representation;

import nl.dennisschroer.messagestub.controller.ExchangeMessageController;
import nl.dennisschroer.messagestub.controller.MessageController;
import nl.dennisschroer.messagestub.exchange.ExchangeMessage;
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

        return representation;
    }

    @Override
    public ExchangeMessagesRepresentation toRepresentation(List<ExchangeMessage> exchangeMessages) {
        ExchangeMessagesRepresentation representation = new ExchangeMessagesRepresentation(exchangeMessages.stream().map(this::toRepresentation).collect(Collectors.toList()));
        representation.add(linkTo(methodOn(ExchangeMessageController.class).getExchangeMessages()).withSelfRel());
        return representation;
    }
}

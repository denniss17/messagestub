package nl.dennisschroer.messagestub.representation;

import nl.dennisschroer.messagestub.exchange.ExchangeMessage;

import java.util.List;

/**
 * @author Dennis Schroer
 * @since 01 Nov 2019
 */
public interface ExchangeMessageRepresentationMapper {
    ExchangeMessageRepresentation toRepresentation(ExchangeMessage exchangeMessage);

    ExchangeMessagesRepresentation toRepresentation(List<ExchangeMessage> exchangeMessages);
}

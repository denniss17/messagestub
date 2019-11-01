package nl.dennisschroer.messagestub.representation;

import org.springframework.hateoas.CollectionModel;

import java.util.List;

/**
 * @author Dennis Schroer
 * @since 01 Nov 2019
 */
public class ExchangeMessagesRepresentation extends CollectionModel<ExchangeMessageRepresentation> {
    public ExchangeMessagesRepresentation(List<ExchangeMessageRepresentation> exchangeMessages) {
        super(exchangeMessages);
    }
}

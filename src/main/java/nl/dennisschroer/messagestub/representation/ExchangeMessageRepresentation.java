package nl.dennisschroer.messagestub.representation;

import nl.dennisschroer.messagestub.exchange.ExchangeMessage;
import org.springframework.hateoas.EntityModel;

/**
 * @author Dennis Schroer
 * @since 01 Nov 2019
 */
public class ExchangeMessageRepresentation extends EntityModel<ExchangeMessage> {
    public ExchangeMessageRepresentation(ExchangeMessage exchangeMessage) {
        super(exchangeMessage);

    }
}

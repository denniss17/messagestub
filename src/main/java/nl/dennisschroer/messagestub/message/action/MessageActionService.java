package nl.dennisschroer.messagestub.message.action;

import nl.dennisschroer.messagestub.exchange.ExchangeMessage;
import nl.dennisschroer.messagestub.message.Message;

import java.util.List;
import java.util.Optional;

public interface MessageActionService {
    /**
     * Zoek de {@link MessageAction} met de gegeven naam die applicable is op het gegeven messageType.
     */
    Optional<MessageAction> getAction(String actionName, String messageType);

    /**
     * Zoekt alle {@link MessageAction}s op die uitgevoerd kunnen worden op {@link Message}s met het gegeven type.
     */
    List<MessageAction> getActionsForMessageType(String messageType);

    /**
     * Zoek de {@link ExchangeMessageAction} met de gegeven naam die applicable is op het gegeven messageType.
     */
    Optional<ExchangeMessageAction> getExchangeAction(String actionName, String messageType);

    /**
     * Zoekt alle {@link ExchangeMessageAction}s op die uitgevoerd kunnen worden op {@link ExchangeMessage}s met het gegeven type.
     */
    List<ExchangeMessageAction> getActionsForExchangeMessageType(String messageType);
}

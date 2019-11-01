package nl.dennisschroer.messagestub.message.action;

import java.util.List;
import java.util.Optional;

public interface MessageActionService {
    /**
     * Zoek de {@link MessageAction} met de gegeven naam die applicable is op het gegeven messageType.
     */
    Optional<MessageAction> getAction(String actionName, String messageType);

    /**
     * Zoekt alle {@link MessageAction}s op die uitgevoerd kunnen worden op messages met het gegeven type.
     */
    List<MessageAction> getActionsForMessageType(String messageType);
}

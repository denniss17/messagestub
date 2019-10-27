package nl.dennisschroer.messagestub.message.action;

import nl.dennisschroer.messagestub.message.action.MessageAction;

import java.util.Optional;

public interface MessageActionService {
    /**
     * Zoek de {@link MessageAction} met de gegeven naam.
     */
    Optional<MessageAction> getAction(String actionName);
}

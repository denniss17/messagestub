package nl.dennisschroer.messagestub.message.action;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageActionServiceImpl implements MessageActionService {

    private final List<MessageAction> messageActions;

    public MessageActionServiceImpl(List<MessageAction> messageActions) {
        this.messageActions = messageActions;
    }

    @Override
    public Optional<MessageAction> getAction(String actionName, String messageType) {
        return messageActions.stream()
                .filter(action -> action.getName().equals(actionName))
                .filter(action -> action.isApplicableToMessageType(messageType))
                .findFirst();
    }
}

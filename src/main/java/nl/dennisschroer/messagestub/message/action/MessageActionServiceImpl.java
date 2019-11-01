package nl.dennisschroer.messagestub.message.action;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Override
    public List<MessageAction> getActionsForMessageType(String messageType) {
        return messageActions.stream()
                .filter(action -> action.isApplicableToMessageType(messageType))
                .collect(Collectors.toList());
    }
}

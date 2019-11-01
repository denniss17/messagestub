package nl.dennisschroer.messagestub.message.action;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageActionServiceImpl implements MessageActionService {

    private final List<MessageAction> messageActions;

    private final List<ExchangeMessageAction> exchangeMessageActions;

    public MessageActionServiceImpl(List<MessageAction> messageActions, List<ExchangeMessageAction> exchangeMessageActions) {
        this.messageActions = messageActions;
        this.exchangeMessageActions = exchangeMessageActions;
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

    @Override
    public Optional<ExchangeMessageAction> getExchangeAction(String actionName, String messageType) {
        return exchangeMessageActions.stream()
                .filter(action -> action.getName().equals(actionName))
                .filter(action -> action.isApplicableToMessageType(messageType))
                .findFirst();
    }

    @Override
    public List<ExchangeMessageAction> getActionsForExchangeMessageType(String messageType) {
        return exchangeMessageActions.stream()
                .filter(action -> action.isApplicableToMessageType(messageType))
                .collect(Collectors.toList());
    }
}

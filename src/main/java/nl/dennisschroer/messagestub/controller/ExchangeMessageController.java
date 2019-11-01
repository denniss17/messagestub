package nl.dennisschroer.messagestub.controller;

import lombok.extern.apachecommons.CommonsLog;
import nl.dennisschroer.messagestub.exchange.ExchangeMessage;
import nl.dennisschroer.messagestub.exchange.ExchangeMessageService;
import nl.dennisschroer.messagestub.message.action.ExchangeMessageAction;
import nl.dennisschroer.messagestub.message.action.MessageActionService;
import nl.dennisschroer.messagestub.representation.exchangemessage.ExchangeMessageRepresentation;
import nl.dennisschroer.messagestub.representation.exchangemessage.ExchangeMessageRepresentationMapper;
import nl.dennisschroer.messagestub.representation.exchangemessage.ExchangeMessagesRepresentation;
import nl.dennisschroer.messagestub.representation.action.MessageActionResultRepresentationMapper;
import nl.dennisschroer.messagestub.representation.action.MessageActionResultRepresentation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;

@Controller
@CommonsLog
@RequestMapping("/api/exchange-messages")
public class ExchangeMessageController {
    private final ExchangeMessageService exchangeMessageService;

    private final MessageActionService messageActionService;

    private final ExchangeMessageRepresentationMapper exchangeMessageRepresentationMapper;

    private final MessageActionResultRepresentationMapper messageActionResultRepresentationMapper;

    public ExchangeMessageController(ExchangeMessageService exchangeMessageService, MessageActionService messageActionService, ExchangeMessageRepresentationMapper exchangeMessageRepresentationMapper, MessageActionResultRepresentationMapper messageActionResultRepresentationMapper) {
        this.exchangeMessageService = exchangeMessageService;
        this.messageActionService = messageActionService;
        this.exchangeMessageRepresentationMapper = exchangeMessageRepresentationMapper;
        this.messageActionResultRepresentationMapper = messageActionResultRepresentationMapper;
    }

    @ResponseBody
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExchangeMessagesRepresentation getExchangeMessages() {
        return exchangeMessageRepresentationMapper.toRepresentation(exchangeMessageService.getExchangeMessages());
    }

    @ResponseBody
    @GetMapping(value = "/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExchangeMessageRepresentation getExchangeMessage(@PathVariable("id") Long id) {
        return exchangeMessageRepresentationMapper.toRepresentation(getExhangeMessageOrThrowException(id));
    }

    @ResponseBody
    @GetMapping(value = "/{id}/action/{actionName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageActionResultRepresentation executeAction(@PathVariable Long id, @PathVariable String actionName) {
        ExchangeMessage exchangeMessage = getExhangeMessageOrThrowException(id);
        ExchangeMessageAction messageAction = getMessageActionOrThrowException(actionName, exchangeMessage);

        try {
            log.info(String.format("Executing action %s (%s) on exchangeMessage %d of type %s",
                    messageAction.getName(), messageAction.getClass().getSimpleName(), exchangeMessage.getId(), exchangeMessage.getMessageType()));

            return messageActionResultRepresentationMapper.toRepresentation(messageAction.execute(exchangeMessage));
        } catch (Exception e) {
            log.error("Error while executing action " + messageAction.getName(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private ExchangeMessage getExhangeMessageOrThrowException(Long id) {
        return exchangeMessageService.getExchangeMessage(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("ExchangeMessage with id %d not found", id)));
    }

    private ExchangeMessageAction getMessageActionOrThrowException(String actionName, ExchangeMessage message) {
        return messageActionService.getExchangeAction(actionName, message.getMessageType()).orElseThrow(() ->
                new EntityNotFoundException(String.format("No MessageAction with name %s found which is applicable to type %s", actionName, message.getMessageType())));
    }
}

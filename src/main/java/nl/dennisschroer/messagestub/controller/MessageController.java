package nl.dennisschroer.messagestub.controller;

import lombok.extern.apachecommons.CommonsLog;
import nl.dennisschroer.messagestub.message.Message;
import nl.dennisschroer.messagestub.message.MessageService;
import nl.dennisschroer.messagestub.message.action.MessageAction;
import nl.dennisschroer.messagestub.message.action.MessageActionResult;
import nl.dennisschroer.messagestub.message.action.MessageActionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@CommonsLog
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    private final MessageActionService messageActionService;

    public MessageController(MessageService messageService, MessageActionService messageActionService) {
        this.messageService = messageService;
        this.messageActionService = messageActionService;
    }

    @ResponseBody
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Message> getMessages() {
        return messageService.getMessages();
    }

    @ResponseBody
    @GetMapping(value = "/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public Message getMessage(@PathVariable("id") Long id) {
        return messageService.getMessage(id).orElseThrow(() -> new EntityNotFoundException(String.format("Message with id %d not found", id)));
    }

    @ResponseBody
    @GetMapping(value = "/{id}/action/{actionName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public MessageActionResult executeAction(@PathVariable("id") Long id, @PathVariable("actionName") String actionName) {
        Message message = messageService.getMessage(id).orElseThrow(() -> new EntityNotFoundException(String.format("Message with id %d not found", id)));
        MessageAction messageAction = messageActionService.getAction(actionName, message.getType()).orElseThrow(() ->
                new EntityNotFoundException(String.format("No MessageAction with name %s found which is applicable to type %s", actionName, message.getType())));

        try {
            log.info(String.format("Executing action %s (%s) on message %d of type %s",
                    messageAction.getName(), messageAction.getClass().getSimpleName(), message.getId(), message.getType()));
            return messageAction.execute(message);
        } catch (Exception e) {
            log.error("Error while executing action " + messageAction.getName(), e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

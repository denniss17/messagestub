package nl.dennisschroer.messagestub.controller;

import nl.dennisschroer.messagestub.message.Message;
import nl.dennisschroer.messagestub.message.MessageService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
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
}

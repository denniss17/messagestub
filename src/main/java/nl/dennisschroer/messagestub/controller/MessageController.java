package nl.dennisschroer.messagestub.controller;

import nl.dennisschroer.messagestub.model.Message;
import nl.dennisschroer.messagestub.service.MessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping()
    public ResponseEntity<List<Message>> getAll() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }
}

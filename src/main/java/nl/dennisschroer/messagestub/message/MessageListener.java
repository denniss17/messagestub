package nl.dennisschroer.messagestub.message;

import lombok.extern.apachecommons.CommonsLog;
import nl.dennisschroer.messagestub.message.event.MessageReceivedEvent;
import nl.dennisschroer.messagestub.message.event.NewMessageEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@CommonsLog
@Component
public class MessageListener {
    private final MessageService messageService;

    public MessageListener(MessageService messageService) {
        this.messageService = messageService;
    }

    @Order(1000) // Order zodat deze altijd als laatste gaat
    @EventListener
    public void onMessageReceived(NewMessageEvent event) {
        messageService.saveMessage(event.getMessage());
        if (event instanceof MessageReceivedEvent) {
            log.info("Message received: " + event.getMessage().toString());
        } else {
            log.info("Message generated: " + event.getMessage().toString());
        }
    }
}

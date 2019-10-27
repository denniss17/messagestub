package nl.dennisschroer.messagestub.message;

import lombok.extern.apachecommons.CommonsLog;
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
    public void onMessageReceived(MessageReceivedEvent event) {
        messageService.saveMessage(event.getMessage());
        log.info("Message ontvangen vanaf " + event.getMessage().getIncomingExchangeMessage().getExchangeType() + ": " + event.getMessage().toString());
    }
}

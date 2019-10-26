package nl.dennisschroer.messagestub.message;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@CommonsLog
@Component
public class MessageListener {
    @EventListener
    public void onMessageReceived(MessageReceivedEvent event) {
        log.info("Message ontvangen vanaf " + event.getExchangeMessage().getExchangeType() + " van type " + event.getMessageType() + ": " + event.getBody());
    }
}

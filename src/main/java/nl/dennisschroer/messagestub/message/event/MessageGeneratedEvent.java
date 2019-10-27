package nl.dennisschroer.messagestub.message.event;

import lombok.Data;
import nl.dennisschroer.messagestub.message.Message;

/**
 * Event wat aangeeft dat er een message is gegenereerd.
 */
@Data
public class MessageGeneratedEvent implements NewMessageEvent {
    private final Message message;

    public MessageGeneratedEvent(Message message) {
        this.message = message;
    }
}

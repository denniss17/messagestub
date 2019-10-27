package nl.dennisschroer.messagestub.message.event;

import lombok.Data;
import nl.dennisschroer.messagestub.message.Message;

/**
 * Event wat aangeeft dat er een message ontvangen is via één van de exchanges.
 */
@Data
public class MessageReceivedEvent implements NewMessageEvent {
    private final Message message;

    public MessageReceivedEvent(Message message) {
        this.message = message;
    }
}

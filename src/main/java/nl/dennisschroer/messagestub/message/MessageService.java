package nl.dennisschroer.messagestub.message;

import nl.dennisschroer.messagestub.exchange.ExchangeMessage;

import java.util.List;

public interface MessageService {
    Message saveMessage(Message message);

    List<Message> getMessages();
}

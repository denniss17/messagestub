package nl.dennisschroer.messagestub.message;

import java.util.List;
import java.util.Optional;

public interface MessageService {
    Message saveMessage(Message message);

    Optional<Message> getMessage(Long id);

    List<Message> getMessages();
}

package nl.dennisschroer.messagestub.service;

import nl.dennisschroer.messagestub.model.Message;

import javax.validation.constraints.NotNull;
import java.util.List;

public interface MessageService {
    @NotNull Message saveMessage(@NotNull Message message);

    List<Message> getAllMessages();
}

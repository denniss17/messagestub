package nl.dennisschroer.messagestub.service;

import nl.dennisschroer.messagestub.model.Message;
import nl.dennisschroer.messagestub.repository.MessageRepository;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @NotNull
    @Override
    public Message saveMessage(@NotNull Message message) {
        return messageRepository.save(message);
    }

    @Override
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }
}

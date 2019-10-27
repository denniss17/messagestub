package nl.dennisschroer.messagestub.message;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @Override
    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    @Override
    public Optional<Message> getMessage(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public List<Message> getMessages() {
        return messageRepository.findAllByOrderByTimestampDesc();
    }
}

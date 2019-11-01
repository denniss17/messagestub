package nl.dennisschroer.messagestub.exchange;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExchangeMessageServiceImpl implements ExchangeMessageService {
    private final ExchangeMessageRepository exchangeMessageRepository;

    public ExchangeMessageServiceImpl(ExchangeMessageRepository exchangeMessageRepository) {
        this.exchangeMessageRepository = exchangeMessageRepository;
    }

    @Override
    public ExchangeMessage saveExchangeMessage(ExchangeMessage exchangeMessage) {
        return exchangeMessageRepository.save(exchangeMessage);
    }

    @Override
    public Optional<ExchangeMessage> getExchangeMessage(Long id) {
        return exchangeMessageRepository.findById(id);
    }

    @Override
    public List<ExchangeMessage> getExchangeMessages() {
        return exchangeMessageRepository.findAllByOrderByTimestampDesc();
    }
}

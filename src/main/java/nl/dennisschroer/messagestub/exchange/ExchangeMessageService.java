package nl.dennisschroer.messagestub.exchange;

import java.util.List;
import java.util.Optional;

public interface ExchangeMessageService {
    ExchangeMessage saveExchangeMessage(ExchangeMessage exchangeMessage);

    Optional<ExchangeMessage> getExchangeMessage(Long id);

    List<ExchangeMessage> getExchangeMessages();
}

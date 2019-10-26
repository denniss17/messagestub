package nl.dennisschroer.messagestub.exchange;

import java.util.List;

public interface ExchangeMessageService {
    ExchangeMessage saveExchangeMessage(ExchangeMessage exchangeMessage);

    List<ExchangeMessage> getExchangeMessages();
}

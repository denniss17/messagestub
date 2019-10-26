package nl.dennisschroer.messagestub.controller;

import nl.dennisschroer.messagestub.exchange.ExchangeMessage;
import nl.dennisschroer.messagestub.exchange.ExchangeMessageService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/api/exchange-messages")
public class ExchangeMessageController {
    private final ExchangeMessageService exchangeMessageService;

    public ExchangeMessageController(ExchangeMessageService exchangeMessageService) {
        this.exchangeMessageService = exchangeMessageService;
    }

    @ResponseBody
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ExchangeMessage> getExchangeMessages() {
        return exchangeMessageService.getExchangeMessages();
    }
}

package nl.dennisschroer.messagestub.controller;

import nl.dennisschroer.messagestub.exchange.ExchangeMessageService;
import nl.dennisschroer.messagestub.representation.ExchangeMessageRepresentation;
import nl.dennisschroer.messagestub.representation.ExchangeMessageRepresentationMapper;
import nl.dennisschroer.messagestub.representation.ExchangeMessagesRepresentation;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityNotFoundException;

@Controller
@RequestMapping("/api/exchange-messages")
public class ExchangeMessageController {
    private final ExchangeMessageService exchangeMessageService;

    private final ExchangeMessageRepresentationMapper exchangeMessageRepresentationMapper;

    public ExchangeMessageController(ExchangeMessageService exchangeMessageService, ExchangeMessageRepresentationMapper exchangeMessageRepresentationMapper) {
        this.exchangeMessageService = exchangeMessageService;
        this.exchangeMessageRepresentationMapper = exchangeMessageRepresentationMapper;
    }

    @ResponseBody
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExchangeMessagesRepresentation getExchangeMessages() {
        return exchangeMessageRepresentationMapper.toRepresentation(exchangeMessageService.getExchangeMessages());
    }

    @ResponseBody
    @GetMapping(value = "/{id}/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ExchangeMessageRepresentation getExchangeMessage(@PathVariable("id") Long id) {
        return exchangeMessageRepresentationMapper.toRepresentation(exchangeMessageService.getExchangeMessage(id).orElseThrow(() ->
                new EntityNotFoundException(String.format("ExchangeMessage with id %d not found", id))));
    }
}

package nl.dennisschroer.messagestub.exchange.ggk.action;

import lombok.extern.apachecommons.CommonsLog;
import nl.dennisschroer.messagestub.exchange.ExchangeMessage;
import nl.dennisschroer.messagestub.exchange.ExchangeMessageService;
import nl.dennisschroer.messagestub.exchange.MessageDirection;
import nl.dennisschroer.messagestub.exchange.ggk.GgkConstants;
import nl.dennisschroer.messagestub.message.action.ExchangeMessageAction;
import nl.dennisschroer.messagestub.message.action.MessageActionResult;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.transform.StringSource;

import javax.validation.constraints.NotNull;
import javax.xml.transform.Source;
import java.util.Arrays;

/**
 * @author Dennis Schroer
 * @since 01 Nov 2019
 */
@CommonsLog
@Component
public class SendDi01ExchangeMessageAction implements ExchangeMessageAction {
    private final WebServiceTemplate webServiceTemplate = new WebServiceTemplate();

    private final ExchangeMessageService exchangeMessageService;

    public SendDi01ExchangeMessageAction(ExchangeMessageService exchangeMessageService) {
        this.exchangeMessageService = exchangeMessageService;
    }

    @Override
    public boolean isApplicableToMessageType(@NotNull String messageType) {
        return Arrays.asList("Di01", "Du01", "Fo01").contains(messageType);
    }

    @Override
    public String getName() {
        return "sendViaGgk";
    }

    @Override
    public String getDescription() {
        return "Verstuur via de GGK-koppeling";
    }

    @Override
    public MessageActionResult execute(ExchangeMessage message) throws Exception {
        Source source = new StringSource(message.getBody());
        StringResult result = new StringResult();
        String soapAction;

        switch (message.getMessageType()) {
            case "Di01":
                soapAction = GgkConstants.SOAP_ACTION_DI01;
                break;
            case "Du01":
                soapAction = GgkConstants.SOAP_ACTION_DU01;
                break;
            case "Fo01":
                soapAction = GgkConstants.SOAP_ACTION_FO01;
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + message.getMessageType());
        }

        log.info("GGK: versturen: " + message.toString());

        // Send and receive
        try {
            webServiceTemplate.sendSourceAndReceiveToResult("http://esb.ewout-ieb.gidso.test/soap", source, new SoapActionCallback(soapAction), result);
        } catch (WebServiceClientException exception) {
            log.error("Fout bij het uitvoeren van " + getName(), exception);

            MessageActionResult actionResult = new MessageActionResult();
            actionResult.setError(exception.getMessage());
            return actionResult;
        }

        // Save result
        ExchangeMessage resultMessage = new ExchangeMessage("GGK", "Onbekend", MessageDirection.OUT);
        resultMessage.setBody(result.toString());
        resultMessage = exchangeMessageService.saveExchangeMessage(resultMessage);

        log.info("GGK: antwoord ontvangen: " + resultMessage.toString());

        return new MessageActionResult(resultMessage);
    }
}

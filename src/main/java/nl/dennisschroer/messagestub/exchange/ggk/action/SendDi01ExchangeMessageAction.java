package nl.dennisschroer.messagestub.exchange.ggk.action;

import lombok.extern.apachecommons.CommonsLog;
import nl.dennisschroer.messagestub.MarshallUtil;
import nl.dennisschroer.messagestub.exchange.ExchangeMessage;
import nl.dennisschroer.messagestub.exchange.ExchangeMessageService;
import nl.dennisschroer.messagestub.exchange.MessageDirection;
import nl.dennisschroer.messagestub.exchange.ggk.GgkConfiguration;
import nl.dennisschroer.messagestub.exchange.ggk.GgkConstants;
import nl.dennisschroer.messagestub.message.action.ExchangeMessageAction;
import nl.dennisschroer.messagestub.message.action.MessageActionResult;
import org.springframework.stereotype.Component;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.soap.client.SoapFaultClientException;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import org.springframework.xml.transform.StringResult;
import org.springframework.xml.transform.StringSource;

import javax.validation.constraints.NotNull;
import javax.xml.namespace.QName;
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

    private final GgkConfiguration ggkConfiguration;

    public SendDi01ExchangeMessageAction(ExchangeMessageService exchangeMessageService, GgkConfiguration ggkConfiguration) {
        this.exchangeMessageService = exchangeMessageService;
        this.ggkConfiguration = ggkConfiguration;
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

        log.info("GGK: versturen naar " + ggkConfiguration.getEndpoint() + ": " + message.toString());

        ExchangeMessage resultMessage = null;

        // Send and receive
        try {
            webServiceTemplate.sendSourceAndReceiveToResult(ggkConfiguration.getEndpoint(), source, new SoapActionCallback(soapAction), result);
        } catch (WebServiceClientException exception) {
            log.error("Fout bij het uitvoeren van " + getName(), exception);

            if (exception instanceof SoapFaultClientException) {
                QName name = ((SoapFaultClientException) exception).getSoapFault().getName();
                Source soapFaultSource = ((SoapFaultClientException) exception).getSoapFault().getSource();
                // TODO hier gaat het nog mis
                String body = MarshallUtil.getXmlFromSource(soapFaultSource, name);

                // Save result
                resultMessage = new ExchangeMessage("GGK", "SoapFault", MessageDirection.IN);
                resultMessage.setBody(body);
                resultMessage = exchangeMessageService.saveExchangeMessage(resultMessage);
            }

            MessageActionResult actionResult = (resultMessage != null) ? new MessageActionResult(resultMessage) : new MessageActionResult();
            actionResult.setError(exception.getMessage());
            return actionResult;
        }

        // Save result
        resultMessage = new ExchangeMessage("GGK", "Onbekend", MessageDirection.IN);
        resultMessage.setBody(result.toString());
        resultMessage = exchangeMessageService.saveExchangeMessage(resultMessage);

        // Update request
        message.setResponseMessage(resultMessage);
        message.setPeerUrl(ggkConfiguration.getEndpoint());
        exchangeMessageService.saveExchangeMessage(message);

        log.info("GGK: antwoord ontvangen: " + resultMessage.toString());

        return new MessageActionResult(resultMessage);
    }
}

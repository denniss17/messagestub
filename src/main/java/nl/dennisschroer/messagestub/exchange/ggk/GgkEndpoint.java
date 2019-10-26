package nl.dennisschroer.messagestub.exchange.ggk;

import lombok.extern.apachecommons.CommonsLog;
import nl.dennisschroer.messagestub.MarshallUtil;
import nl.dennisschroer.messagestub.MessageDirection;
import nl.dennisschroer.messagestub.exchange.ExchangeMessage;
import nl.dennisschroer.messagestub.exchange.ExchangeMessageService;
import nl.dennisschroer.messagestub.message.MessageReceivedEvent;
import nl.egem.stuf.stuf0301.Bv03Bericht;
import nl.egem.stuf.stuf0301.Fo01Bericht;
import nl.stufstandaarden.koppelvlak.ggk0210.EnvelopHeenberichtGgkDi01;
import nl.stufstandaarden.koppelvlak.ggk0210.EnvelopRetourberichtGgkDu01;
import nl.stufstandaarden.koppelvlak.ggk0210.ParametersGgkberichten;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.server.endpoint.annotation.SoapAction;

import javax.xml.bind.JAXBException;

@Endpoint
@Component
@CommonsLog
public class GgkEndpoint {
    private final ExchangeMessageService exchangeMessageService;
    private final GgkResponseGenerator ggkResponseGenerator;
    private final ApplicationEventPublisher eventPublisher;

    public GgkEndpoint(ExchangeMessageService exchangeMessageService, GgkResponseGenerator ggkResponseGenerator, ApplicationEventPublisher eventPublisher) {
        this.exchangeMessageService = exchangeMessageService;
        this.ggkResponseGenerator = ggkResponseGenerator;
        this.eventPublisher = eventPublisher;
    }

    @ResponsePayload
    @SoapAction("http://www.stufstandaarden.nl/koppelvlak/ggk0210/ggk_Di01")
    public Bv03Bericht postDi01(@RequestPayload EnvelopHeenberichtGgkDi01 bericht) throws GgkException {
        try {
            // Save request
            ExchangeMessage exchangeMessage = new ExchangeMessage("GGK", "Di01", MessageDirection.IN);
            exchangeMessage.setBody(MarshallUtil.marshall(bericht));
            exchangeMessage = exchangeMessageService.saveExchangeMessage(exchangeMessage);

            log.info("GGK: Di01 ontvangen: " + exchangeMessage.toString());

            // Extract message
            GgkBerichtFunctie berichtFunctie = bepaalBerichtFunctie(bericht);
            String berichtData = bepaalMessageBody(bericht);

            // Publish message
            eventPublisher.publishEvent(new MessageReceivedEvent(berichtFunctie.getMessageType(), berichtData));

            // Create response
            Bv03Bericht response = ggkResponseGenerator.generateResponse(bericht);

            // Save response
            ExchangeMessage responseExchangeMessage = new ExchangeMessage("GGK", "Bv03", MessageDirection.OUT);
            responseExchangeMessage.setBody(MarshallUtil.marshall(response));
            responseExchangeMessage.setRequestMessage(exchangeMessage);
            responseExchangeMessage = exchangeMessageService.saveExchangeMessage(responseExchangeMessage);
            log.info("GGK: antwoorden met Bv03 : " + responseExchangeMessage.toString());

            // Return response
            return response;
        } catch (JAXBException e) {
            throw new GgkException(bericht.getStuurgegevens(), e);
        }
    }

    @ResponsePayload
    @SoapAction("http://www.stufstandaarden.nl/koppelvlak/ggk0210/ggk_Du01")
    public Bv03Bericht postDu01(@RequestPayload EnvelopRetourberichtGgkDu01 retourBericht) throws GgkException {
        try {
            // Save request
            ExchangeMessage exchangeMessage = new ExchangeMessage("GGK", "Du01", MessageDirection.IN);
            exchangeMessage.setBody(MarshallUtil.marshall(retourBericht));
            exchangeMessage = exchangeMessageService.saveExchangeMessage(exchangeMessage);

            log.info("GGK: Du01 ontvangen: " + exchangeMessage.toString());

            // Extract message
            GgkBerichtFunctie berichtFunctie = bepaalBerichtFunctie(retourBericht);
            String berichtData = bepaalMessageBody(retourBericht);

            // Publish message
            eventPublisher.publishEvent(new MessageReceivedEvent(berichtFunctie.getMessageType(), berichtData));

            // Create response
            Bv03Bericht response = ggkResponseGenerator.generateResponse(retourBericht);

            // Save response
            ExchangeMessage responseExchangeMessage = new ExchangeMessage("GGK", "Bv03", MessageDirection.OUT);
            responseExchangeMessage.setBody(MarshallUtil.marshall(response));
            responseExchangeMessage.setRequestMessage(exchangeMessage);
            responseExchangeMessage = exchangeMessageService.saveExchangeMessage(responseExchangeMessage);
            log.info("GGK: antwoorden met Bv03 : " + responseExchangeMessage.toString());

            // Return response
            return response;
        } catch (JAXBException e) {
            throw new GgkException(retourBericht.getStuurgegevens(), e);
        }
    }

    @ResponsePayload
    @SoapAction("http://www.egem.nl/StUF/StUF0301/Fo01")
    public Bv03Bericht postFo01(@RequestPayload Fo01Bericht foutBericht) throws GgkException {
        try {
            // Save request
            ExchangeMessage exchangeMessage = new ExchangeMessage("GGK", "Fo01", MessageDirection.IN);
            exchangeMessage.setBody(MarshallUtil.marshall(foutBericht));
            exchangeMessage = exchangeMessageService.saveExchangeMessage(exchangeMessage);

            log.info("GGK: Fo01 ontvangen: " + exchangeMessage.toString());

            // Create response
            Bv03Bericht response = ggkResponseGenerator.generateResponse(foutBericht);

            // Save response
            ExchangeMessage responseExchangeMessage = new ExchangeMessage("GGK", "Bv03", MessageDirection.OUT);
            responseExchangeMessage.setBody(MarshallUtil.marshall(response));
            responseExchangeMessage.setRequestMessage(exchangeMessage);
            responseExchangeMessage = exchangeMessageService.saveExchangeMessage(responseExchangeMessage);
            log.info("GGK: antwoorden met Bv03 : " + responseExchangeMessage.toString());

            // Return response
            return response;
        } catch (JAXBException e) {
            throw new GgkException(foutBericht.getStuurgegevens(), e);
        }
    }

    private GgkBerichtFunctie bepaalBerichtFunctie(EnvelopHeenberichtGgkDi01 bericht) throws GgkException {
        try {
            return GgkBerichtFunctie.valueOf(bericht.getStuurgegevens().getFunctie());
        } catch (IllegalArgumentException e) {
            throw new GgkException(e);
        }
    }

    private GgkBerichtFunctie bepaalBerichtFunctie(EnvelopRetourberichtGgkDu01 retourBericht) throws GgkException {
        try {
            return GgkBerichtFunctie.valueOf(retourBericht.getStuurgegevens().getFunctie());
        } catch (IllegalArgumentException e) {
            throw new GgkException(e);
        }
    }


    private String bepaalMessageBody(EnvelopHeenberichtGgkDi01 bericht) {
        return bepaalMessageBody(bericht.getParameters());
    }

    private String bepaalMessageBody(EnvelopRetourberichtGgkDu01 retourBericht) {
        return bepaalMessageBody(retourBericht.getParameters());
    }

    private String bepaalMessageBody(ParametersGgkberichten parametersGgkberichten) {
        byte[] body = parametersGgkberichten.getBericht().getXmlBestand();
        return new String(body == null ? parametersGgkberichten.getBericht().getTekstBestand().getValue() : body);
    }
}

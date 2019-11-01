package nl.dennisschroer.messagestub.exchange.ggk;

import lombok.extern.apachecommons.CommonsLog;
import nl.dennisschroer.messagestub.MarshallUtil;
import nl.dennisschroer.messagestub.exchange.MessageDirection;
import nl.dennisschroer.messagestub.exchange.ExchangeMessage;
import nl.dennisschroer.messagestub.exchange.ExchangeMessageService;
import nl.dennisschroer.messagestub.message.Message;
import nl.dennisschroer.messagestub.message.event.MessageReceivedEvent;
import nl.egem.stuf.stuf0301.Bv03Bericht;
import nl.egem.stuf.stuf0301.Fo01Bericht;
import nl.egem.stuf.stuf0301.Stuurgegevens;
import nl.stufstandaarden.koppelvlak.ggk0210.EnvelopHeenberichtGgkDi01;
import nl.stufstandaarden.koppelvlak.ggk0210.EnvelopRetourberichtGgkDu01;
import nl.stufstandaarden.koppelvlak.ggk0210.ParametersGgkberichten;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.server.endpoint.annotation.SoapAction;
import org.springframework.ws.transport.context.TransportContext;
import org.springframework.ws.transport.context.TransportContextHolder;
import org.springframework.ws.transport.http.HttpServletConnection;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
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
    @SoapAction(GgkConstants.SOAP_ACTION_DI01)
    public Bv03Bericht postDi01(@RequestPayload EnvelopHeenberichtGgkDi01 bericht) throws GgkException {
        try {
            // Save request
            ExchangeMessage exchangeMessage = saveRequest("Di01", MarshallUtil.marshall(bericht));
            log.info("GGK: Di01 ontvangen: " + exchangeMessage.toString());

            // Publish message
            Message message = new Message(bepaalMessageType(bericht), bepaalMessageBody(bericht));
            message.getMeta().setReferentienummer(bericht.getStuurgegevens().getReferentienummer());
            message.getMeta().setApplicatieNaam(bericht.getStuurgegevens().getZender().getApplicatie());
            eventPublisher.publishEvent(new MessageReceivedEvent(message));

            // Link message
            exchangeMessage.setMessage(message);
            exchangeMessageService.saveExchangeMessage(exchangeMessage);

            // Create response
            Bv03Bericht response = ggkResponseGenerator.generateResponse(bericht);

            // Save response
            ExchangeMessage responseExchangeMessage = saveResponse(exchangeMessage, "Bv03", MarshallUtil.marshall(response));
            log.info("GGK: antwoorden met Bv03 : " + responseExchangeMessage.toString());

            // Return response
            return response;
        } catch (JAXBException e) {
            throw new GgkException(bericht.getStuurgegevens(), e);
        }
    }

    @ResponsePayload
    @SoapAction(GgkConstants.SOAP_ACTION_DU01)
    public Bv03Bericht postDu01(@RequestPayload EnvelopRetourberichtGgkDu01 retourBericht) throws GgkException {
        try {
            // Save request
            ExchangeMessage exchangeMessage = saveRequest("Du01", MarshallUtil.marshall(retourBericht));
            log.info("GGK: Du01 ontvangen: " + exchangeMessage.toString());

            // Publish message
            Message message = new Message(bepaalMessageType(retourBericht), bepaalMessageBody(retourBericht));
            message.getMeta().setReferentienummer(retourBericht.getStuurgegevens().getReferentienummer());
            message.getMeta().setApplicatieNaam(retourBericht.getStuurgegevens().getZender().getApplicatie());
            eventPublisher.publishEvent(new MessageReceivedEvent(message));

            // Link message
            exchangeMessage.setMessage(message);
            exchangeMessageService.saveExchangeMessage(exchangeMessage);

            // Create response
            Bv03Bericht response = ggkResponseGenerator.generateResponse(retourBericht);

            // Save response
            ExchangeMessage responseExchangeMessage = saveResponse(exchangeMessage, "Bv03", MarshallUtil.marshall(response));
            log.info("GGK: antwoorden met Bv03: " + responseExchangeMessage.toString());

            // Return response
            return response;
        } catch (JAXBException e) {
            throw new GgkException(retourBericht.getStuurgegevens(), e);
        }
    }

    @ResponsePayload
    @SoapAction(GgkConstants.SOAP_ACTION_FO01)
    public Bv03Bericht postFo01(@RequestPayload Fo01Bericht foutBericht) throws GgkException {
        try {
            // Save request
            ExchangeMessage exchangeMessage = saveRequest("Fo01", MarshallUtil.marshall(foutBericht));
            log.info("GGK: Fo01 ontvangen: " + exchangeMessage.toString());

            // Create response
            Bv03Bericht response = ggkResponseGenerator.generateResponse(foutBericht);

            // Save response
            ExchangeMessage responseExchangeMessage = saveResponse(exchangeMessage, "Bv03", MarshallUtil.marshall(response));
            log.info("GGK: antwoorden met Bv03: " + responseExchangeMessage.toString());

            // Return response
            return response;
        } catch (JAXBException e) {
            throw new GgkException(foutBericht.getStuurgegevens(), e);
        }
    }

    @NotNull
    private String bepaalMessageType(EnvelopHeenberichtGgkDi01 bericht) {
        return bepaalMessageType(bericht.getStuurgegevens());
    }

    @NotNull
    private String bepaalMessageType(EnvelopRetourberichtGgkDu01 retourBericht) {
        return bepaalMessageType(retourBericht.getStuurgegevens());
    }

    @NotNull
    private String bepaalMessageType(Stuurgegevens stuurgegevens) {
        return stuurgegevens.getFunctie();
    }

    @NotNull
    private String bepaalMessageBody(EnvelopHeenberichtGgkDi01 bericht) {
        return bepaalMessageBody(bericht.getParameters());
    }

    @NotNull
    private String bepaalMessageBody(EnvelopRetourberichtGgkDu01 retourBericht) {
        return bepaalMessageBody(retourBericht.getParameters());
    }

    @NotNull
    private String bepaalMessageBody(ParametersGgkberichten parametersGgkberichten) {
        byte[] body = parametersGgkberichten.getBericht().getXmlBestand();
        return new String(body == null ? parametersGgkberichten.getBericht().getTekstBestand().getValue() : body);
    }

    @NotNull
    private ExchangeMessage saveRequest(String messageType, String body) {
        ExchangeMessage exchangeMessage = new ExchangeMessage("GGK", messageType, MessageDirection.IN);
        exchangeMessage.setBody(body);
        exchangeMessage.setPeerUrl(determineClientIp());
        return exchangeMessageService.saveExchangeMessage(exchangeMessage);
    }

    @NotNull
    private ExchangeMessage saveResponse(ExchangeMessage requestMessage, String messageType, String body) {
        // Save response
        ExchangeMessage responseExchangeMessage = new ExchangeMessage("GGK", messageType, MessageDirection.OUT);
        responseExchangeMessage.setBody(body);
        responseExchangeMessage.setPeerUrl(determineClientIp());
        responseExchangeMessage = exchangeMessageService.saveExchangeMessage(responseExchangeMessage);

        // Add response to request
        requestMessage.setResponseMessage(responseExchangeMessage);
        exchangeMessageService.saveExchangeMessage(requestMessage);

        return responseExchangeMessage;
    }

    @Nullable
    private String determineClientIp() {
        TransportContext transportContext = TransportContextHolder.getTransportContext();
        if (transportContext != null && transportContext.getConnection() instanceof HttpServletConnection) {
            HttpServletRequest request = ((HttpServletConnection) transportContext.getConnection()).getHttpServletRequest();
            return request.getRemoteAddr();
        }

        return null;
    }
}


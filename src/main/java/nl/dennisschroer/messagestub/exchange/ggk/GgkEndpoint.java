package nl.dennisschroer.messagestub.exchange.ggk;

import nl.dennisschroer.messagestub.MessageDirection;
import nl.dennisschroer.messagestub.exchange.ExchangeMessage;
import nl.dennisschroer.messagestub.exchange.ExchangeMessageService;
import nl.egem.stuf.stuf0301.Bv03Bericht;
import nl.egem.stuf.stuf0301.Fo01Bericht;
import nl.stufstandaarden.koppelvlak.ggk0210.EnvelopHeenberichtGgkDi01;
import nl.stufstandaarden.koppelvlak.ggk0210.EnvelopRetourberichtGgkDu01;
import org.springframework.stereotype.Component;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import org.springframework.ws.soap.server.endpoint.annotation.SoapAction;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

@Endpoint
@Component
public class GgkEndpoint {
    private final ExchangeMessageService exchangeMessageService;
    private final GgkResponseGenerator ggkResponseGenerator;

    public GgkEndpoint(ExchangeMessageService exchangeMessageService, GgkResponseGenerator ggkResponseGenerator) {
        this.exchangeMessageService = exchangeMessageService;
        this.ggkResponseGenerator = ggkResponseGenerator;
    }

    @ResponsePayload
    @SoapAction("http://www.stufstandaarden.nl/koppelvlak/ggk0210/ggk_Di01")
    public Bv03Bericht postDi01(@RequestPayload EnvelopHeenberichtGgkDi01 bericht) throws JAXBException {
        // Save request
        ExchangeMessage exchangeMessage = new ExchangeMessage("GGK", "Di01", MessageDirection.IN);
        exchangeMessage.setBody(marshall(bericht));
        exchangeMessageService.saveExchangeMessage(exchangeMessage);

        // Create response
        Bv03Bericht response = ggkResponseGenerator.generateResponse(bericht);

        // Save response
        ExchangeMessage responseExchangeMessage = new ExchangeMessage("GGK", "Bv03", MessageDirection.OUT);
        exchangeMessage.setBody(marshall(response));
        exchangeMessage.setRequestMessage(exchangeMessage);
        exchangeMessageService.saveExchangeMessage(responseExchangeMessage);

        // Return response
        return response;
    }

    @ResponsePayload
    @SoapAction("http://www.stufstandaarden.nl/koppelvlak/ggk0210/ggk_Du01")
    public Bv03Bericht postDu01(@RequestPayload EnvelopRetourberichtGgkDu01 retourBericht) throws JAXBException {
        ExchangeMessage exchangeMessage = new ExchangeMessage("GGK", "Du01", MessageDirection.IN);
        exchangeMessage.setBody(marshall(retourBericht));
        exchangeMessageService.saveExchangeMessage(exchangeMessage);



        return ggkResponseGenerator.generateResponse(retourBericht);
    }

    @ResponsePayload
    @SoapAction("http://www.egem.nl/StUF/StUF0301/Fo01")
    public Bv03Bericht postFo01(@RequestPayload Fo01Bericht foutBericht) throws JAXBException {
        ExchangeMessage exchangeMessage = new ExchangeMessage("GGK", "Fo01", MessageDirection.IN);
        exchangeMessage.setBody(marshall(foutBericht));
        exchangeMessageService.saveExchangeMessage(exchangeMessage);

        return ggkResponseGenerator.generateResponse(foutBericht);
    }

    /**
     * Marshall java naar XML.
     */
    private String marshall(Object bericht) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(bericht.getClass());
        Marshaller jaxbMarshaller = jaxbContext.createMarshaller();
        jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter writer = new StringWriter();
        jaxbMarshaller.marshal(bericht, writer);

        return writer.toString();
    }
}

package nl.dennisschroer.messagestub.exchange.ggk;

import lombok.extern.apachecommons.CommonsLog;
import nl.dennisschroer.messagestub.MarshallUtil;
import nl.dennisschroer.messagestub.MessageDirection;
import nl.dennisschroer.messagestub.exchange.ExchangeMessage;
import nl.dennisschroer.messagestub.exchange.ExchangeMessageService;
import nl.egem.stuf.stuf0301.Fo03Bericht;
import org.springframework.stereotype.Component;
import org.springframework.ws.context.MessageContext;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SimpleSoapExceptionResolver;

import javax.xml.bind.JAXBException;
import javax.xml.transform.Result;

/**
 * Custom exceptionresolver die zorgt dat er bij een {@link GgkException} een {@link Fo03Bericht} in de soap fault komt.
 */
@CommonsLog
@Component("exceptionResolver")
class GgkExceptionResolver extends SimpleSoapExceptionResolver {
    private final GgkResponseGenerator ggkResponseGenerator;
    private final ExchangeMessageService exchangeMessageService;

    GgkExceptionResolver(GgkResponseGenerator ggkResponseGenerator, ExchangeMessageService exchangeMessageService) {
        this.ggkResponseGenerator = ggkResponseGenerator;
        this.exchangeMessageService = exchangeMessageService;
    }

    @Override
    protected void customizeFault(MessageContext messageContext, Object endpoint, Exception exception, SoapFault fault) {
        log.error(exception.getMessage(), exception);

        if (exception instanceof GgkException) {
            final SoapFaultDetail faultDetail = fault.addFaultDetail();
            final Result result = faultDetail.getResult();

            Fo03Bericht fo03Bericht = ggkResponseGenerator.generateFo03Bericht(((GgkException) exception).getStuurgegevens(), exception);

            try {
                // Om deze fout ook inzichtelijk te maken in de api, slaan we de gegegenereerde fout op.
                ExchangeMessage exchangeMessage = new ExchangeMessage("GGK", "Fo03", MessageDirection.OUT);
                exchangeMessage.setBody(MarshallUtil.marshall(fo03Bericht));
                exchangeMessage = exchangeMessageService.saveExchangeMessage(exchangeMessage);

                log.error("GGK: Fo03 gegenereerd: " + exchangeMessage.toString());

                MarshallUtil.marshall(fo03Bericht, result);
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }
}

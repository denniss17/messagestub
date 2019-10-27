package nl.dennisschroer.messagestub;

import lombok.Builder;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.WebServiceMessageFactory;
import org.springframework.ws.soap.SoapMessage;
import org.springframework.ws.test.server.RequestCreator;
import org.springframework.ws.test.support.creator.SoapEnvelopeMessageCreator;

import javax.xml.transform.Source;
import java.io.IOException;

/**
 * Builder voor het maken van soap requests
 * <p>
 * Gebaseerd op WebServiceMessageCreatorAdapter in {@link org.springframework.ws.test.server.RequestCreators}.
 * De standaard creator is vrij basic, deze creator heeft ondersteuning voor meer customizations.
 */
@Builder
public class SoapActionRequestCreator implements RequestCreator {
    private Source soapEnvelope;

    private String soapAction;

    @Override
    public WebServiceMessage createRequest(WebServiceMessageFactory webServiceMessageFactory) throws IOException {
        if (soapEnvelope != null) {
            WebServiceMessage message = new SoapEnvelopeMessageCreator(soapEnvelope).createMessage(webServiceMessageFactory);


            if (soapAction != null) {
                SoapMessage soapMessage = (SoapMessage) message;
                soapMessage.setSoapAction(soapAction);
            }

            return message;
        }

        return null;
    }
}

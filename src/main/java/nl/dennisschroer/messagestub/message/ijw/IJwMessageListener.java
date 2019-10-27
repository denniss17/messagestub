package nl.dennisschroer.messagestub.message.ijw;

import lombok.extern.apachecommons.CommonsLog;
import nl.dennisschroer.messagestub.MarshallUtil;
import nl.dennisschroer.messagestub.message.Message;
import nl.dennisschroer.messagestub.message.MessageReceivedEvent;
import nl.istandaarden.generated.ijw.ijw301.IJW301Bericht;
import nl.istandaarden.generated.ijw.ijw302.IJW302Bericht;
import nl.istandaarden.generated.ijw.ijw303.IJW303Bericht;
import nl.istandaarden.generated.ijw.ijw305.IJW305Bericht;
import nl.istandaarden.generated.ijw.ijw306.IJW306Bericht;
import nl.istandaarden.generated.ijw.ijw307.IJW307Bericht;
import nl.istandaarden.generated.ijw.ijw308.IJW308Bericht;
import nl.istandaarden.generated.ijw.ijw315.IJW315Bericht;
import nl.istandaarden.generated.ijw.ijw316.IJW316Bericht;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;

@Component
@CommonsLog
public class IJwMessageListener {
    @Order(1)
    @EventListener
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();

        try {
            switch (message.getType()) {
                case "IJW301":
                    IJW301Bericht ijw301Bericht = MarshallUtil.unmarshall(message.getBody(), IJW301Bericht.class);
                    message.getMeta().setAgbCode(ijw301Bericht.getHeader().getOntvanger());
                    message.getMeta().setBsn(ijw301Bericht.getClient().getBsn());
                    message.getMeta().setBeschikkingsnummer(ijw301Bericht.getClient().getBeschikking().getBeschikkingNummer());
                    break;
                case "IJW302":
                    IJW302Bericht ijw302Bericht = MarshallUtil.unmarshall(message.getBody(), IJW302Bericht.class);
                    message.getMeta().setAgbCode(ijw302Bericht.getHeader().getOntvanger());
                    message.getMeta().setBsn(ijw302Bericht.getClient().getBsn());
                    message.getMeta().setBeschikkingsnummer(ijw302Bericht.getClient().getBeschikking().getBeschikkingNummer());
                    break;
                case "IJW303":
                    IJW303Bericht ijw303Bericht = MarshallUtil.unmarshall(message.getBody(), IJW303Bericht.class);
                    message.getMeta().setAgbCode(ijw303Bericht.getHeader().getAanbieder());
                    break;
                case "IJW305":
                    IJW305Bericht ijw305Bericht = MarshallUtil.unmarshall(message.getBody(), IJW305Bericht.class);
                    message.getMeta().setAgbCode(ijw305Bericht.getHeader().getOntvanger());
                    message.getMeta().setBsn(ijw305Bericht.getClient().getBsn());
                    if (ijw305Bericht.getClient().getStartProducten().getStartProduct().size() > 0) {
                        message.getMeta().setBeschikkingsnummer(ijw305Bericht.getClient().getStartProducten().getStartProduct().get(0).getBeschikkingNummer());
                    }
                    break;
                case "IJW306":
                    IJW306Bericht ijw306Bericht = MarshallUtil.unmarshall(message.getBody(), IJW306Bericht.class);
                    message.getMeta().setAgbCode(ijw306Bericht.getHeader().getOntvanger());
                    message.getMeta().setBsn(ijw306Bericht.getClient().getBsn());
                    if (ijw306Bericht.getClient().getStartProducten().getStartProduct().size() > 0) {
                        message.getMeta().setBeschikkingsnummer(ijw306Bericht.getClient().getStartProducten().getStartProduct().get(0).getBeschikkingNummer());
                    }
                    break;
                case "IJW307":
                    IJW307Bericht ijw307Bericht = MarshallUtil.unmarshall(message.getBody(), IJW307Bericht.class);
                    message.getMeta().setAgbCode(ijw307Bericht.getHeader().getOntvanger());
                    message.getMeta().setBsn(ijw307Bericht.getClient().getBsn());
                    if (ijw307Bericht.getClient().getStopProducten().getStopProduct().size() > 0) {
                        message.getMeta().setBeschikkingsnummer(ijw307Bericht.getClient().getStopProducten().getStopProduct().get(0).getBeschikkingNummer());
                    }
                    break;
                case "IJW308":
                    IJW308Bericht ijw308Bericht = MarshallUtil.unmarshall(message.getBody(), IJW308Bericht.class);
                    message.getMeta().setAgbCode(ijw308Bericht.getHeader().getOntvanger());
                    message.getMeta().setBsn(ijw308Bericht.getClient().getBsn());
                    if (ijw308Bericht.getClient().getStopProducten().getStopProduct().size() > 0) {
                        message.getMeta().setBeschikkingsnummer(ijw308Bericht.getClient().getStopProducten().getStopProduct().get(0).getBeschikkingNummer());
                    }
                    break;
                case "IJW315":
                    IJW315Bericht ijw315Bericht = MarshallUtil.unmarshall(message.getBody(), IJW315Bericht.class);
                    message.getMeta().setAgbCode(ijw315Bericht.getHeader().getOntvanger());
                    message.getMeta().setBsn(ijw315Bericht.getClient().getBsn());
                    if (ijw315Bericht.getClient().getAangevraagdeProducten().getAangevraagdProduct().size() > 0) {
                        message.getMeta().setBeschikkingsnummer(ijw315Bericht.getClient().getAangevraagdeProducten().getAangevraagdProduct().get(0).getBeschikkingNummer());
                    }
                    break;
                case "IJW316":
                    IJW316Bericht ijw316Bericht = MarshallUtil.unmarshall(message.getBody(), IJW316Bericht.class);
                    message.getMeta().setAgbCode(ijw316Bericht.getHeader().getOntvanger());
                    message.getMeta().setBsn(ijw316Bericht.getClient().getBsn());
                    if (ijw316Bericht.getClient().getAangevraagdeProducten().getAangevraagdProduct().size() > 0) {
                        message.getMeta().setBeschikkingsnummer(ijw316Bericht.getClient().getAangevraagdeProducten().getAangevraagdProduct().get(0).getBeschikkingNummer());
                    }
                    break;
            }
        } catch (JAXBException e) {
            log.error("Error while parsing message", e);
            message.getMeta().setError(e.getClass().getSimpleName() + " while parsing message: " + e.getMessage());
        }
    }
}

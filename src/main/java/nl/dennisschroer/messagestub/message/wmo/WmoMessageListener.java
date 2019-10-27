package nl.dennisschroer.messagestub.message.wmo;

import lombok.extern.apachecommons.CommonsLog;
import nl.dennisschroer.messagestub.MarshallUtil;
import nl.dennisschroer.messagestub.message.Message;
import nl.dennisschroer.messagestub.message.MessageReceivedEvent;
import nl.istandaarden.generated.wmo.wmo301.WMO301Bericht;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;

@Component
@CommonsLog
public class WmoMessageListener {
    @Order(1)
    @EventListener
    public void onMessageReceived(MessageReceivedEvent event) {
        Message message = event.getMessage();
        if (message.getType().equals("WMO301")) {
            log.info("Message verwerken van type " + message.getType());
            try {
                WMO301Bericht wmo301Bericht = MarshallUtil.unmarshall(message.getBody(), WMO301Bericht.class);
                message.getMeta().setAgbCode(wmo301Bericht.getHeader().getOntvanger());
                message.getMeta().setBsn(wmo301Bericht.getClient().getBsn());
                message.getMeta().setBeschikkingsnummer(wmo301Bericht.getClient().getBeschikking().getBeschikkingNummer());
            } catch (JAXBException e) {
                e.printStackTrace();
            }
        }
    }
}

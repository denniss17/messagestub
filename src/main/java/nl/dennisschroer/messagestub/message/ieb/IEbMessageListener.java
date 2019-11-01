package nl.dennisschroer.messagestub.message.ieb;

import lombok.extern.apachecommons.CommonsLog;
import nl.dennisschroer.messagestub.MarshallUtil;
import nl.dennisschroer.messagestub.message.Message;
import nl.dennisschroer.messagestub.message.event.NewMessageEvent;
import nl.istandaarden.generated.ieb.wmo401.WMO401Bericht;
import nl.istandaarden.generated.ieb.wmo402.WMO402Bericht;
import nl.istandaarden.generated.ieb.wmo403.WMO403Bericht;
import nl.istandaarden.generated.ieb.wmo404.WMO404Bericht;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;

@Component
@CommonsLog
public class IEbMessageListener {
    @Order(1)
    @EventListener
    public void onMessageReceived(NewMessageEvent event) {
        Message message = event.getMessage();

        try {
            switch (message.getType()) {
                case "WMO401":
                    WMO401Bericht wmo401Bericht = MarshallUtil.unmarshall(message.getBody(), WMO401Bericht.class);
                    message.getMeta().setGemeenteCode(wmo401Bericht.getHeader().getAfzender());
                    message.getMeta().setBsn(wmo401Bericht.getClient().getBsn());
                    message.getMeta().setStartnummer(wmo401Bericht.getClient().getStartEigenBijdrage().getEbStartNummer());
                    break;
                case "WMO402":
                    WMO402Bericht wmo402Bericht = MarshallUtil.unmarshall(message.getBody(), WMO402Bericht.class);
                    message.getMeta().setGemeenteCode(wmo402Bericht.getHeader().getAfzender());
                    // Client is optioneel
                    if (wmo402Bericht.getClient() != null) {
                        message.getMeta().setBsn(wmo402Bericht.getClient().getBsn());
                        message.getMeta().setStartnummer(wmo402Bericht.getClient().getStartEigenBijdrage().getEbStartNummer());
                    }
                    break;
                case "WMO403":
                    WMO403Bericht wmo403Bericht = MarshallUtil.unmarshall(message.getBody(), WMO403Bericht.class);
                    message.getMeta().setGemeenteCode(wmo403Bericht.getHeader().getAfzender());
                    message.getMeta().setBsn(wmo403Bericht.getClient().getBsn());
                    message.getMeta().setStartnummer(wmo403Bericht.getClient().getStopEigenBijdrage().getEbStartNummer());
                    break;
                case "WMO404":
                    WMO404Bericht wmo404Bericht = MarshallUtil.unmarshall(message.getBody(), WMO404Bericht.class);
                    message.getMeta().setGemeenteCode(wmo404Bericht.getHeader().getAfzender());
                    // Client is optioneel
                    if (wmo404Bericht.getClient() != null) {
                        message.getMeta().setBsn(wmo404Bericht.getClient().getBsn());
                        message.getMeta().setStartnummer(wmo404Bericht.getClient().getStopEigenBijdrage().getEbStartNummer());
                    }
                    break;
            }
        } catch (JAXBException e) {
            log.error("Error while parsing message", e);
            message.getMeta().setError(e.getClass().getSimpleName() + " while parsing message: " + e.getMessage());
        }
    }
}

package nl.dennisschroer.messagestub.message.iwmo;

import lombok.extern.apachecommons.CommonsLog;
import nl.dennisschroer.messagestub.MarshallUtil;
import nl.dennisschroer.messagestub.message.Message;
import nl.dennisschroer.messagestub.message.event.NewMessageEvent;
import nl.istandaarden.generated.iwmo.wmo301.WMO301Bericht;
import nl.istandaarden.generated.iwmo.wmo302.WMO302Bericht;
import nl.istandaarden.generated.iwmo.wmo303.WMO303Bericht;
import nl.istandaarden.generated.iwmo.wmo305.WMO305Bericht;
import nl.istandaarden.generated.iwmo.wmo306.WMO306Bericht;
import nl.istandaarden.generated.iwmo.wmo307.WMO307Bericht;
import nl.istandaarden.generated.iwmo.wmo308.WMO308Bericht;
import nl.istandaarden.generated.iwmo.wmo315.WMO315Bericht;
import nl.istandaarden.generated.iwmo.wmo316.WMO316Bericht;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.xml.bind.JAXBException;

@Component
@CommonsLog
public class IWmoMessageListener {
    @Order(1)
    @EventListener
    public void onMessageReceived(NewMessageEvent event) {
        Message message = event.getMessage();

        try {
            switch (message.getType()) {
                case "WMO301":
                    WMO301Bericht wmo301Bericht = MarshallUtil.unmarshall(message.getBody(), WMO301Bericht.class);
                    message.getMeta().setAgbCode(wmo301Bericht.getHeader().getOntvanger());
                    message.getMeta().setGemeenteCode(wmo301Bericht.getHeader().getAfzender());
                    message.getMeta().setBsn(wmo301Bericht.getClient().getBsn());
                    message.getMeta().setBeschikkingsnummer(wmo301Bericht.getClient().getBeschikking().getBeschikkingNummer());
                    break;
                case "WMO302":
                    WMO302Bericht wmo302Bericht = MarshallUtil.unmarshall(message.getBody(), WMO302Bericht.class);
                    message.getMeta().setAgbCode(wmo302Bericht.getHeader().getOntvanger());
                    message.getMeta().setGemeenteCode(wmo302Bericht.getHeader().getAfzender());
                    message.getMeta().setBsn(wmo302Bericht.getClient().getBsn());
                    message.getMeta().setBeschikkingsnummer(wmo302Bericht.getClient().getBeschikking().getBeschikkingNummer());
                    break;
                case "WMO303":
                    WMO303Bericht wmo303Bericht = MarshallUtil.unmarshall(message.getBody(), WMO303Bericht.class);
                    message.getMeta().setAgbCode(wmo303Bericht.getHeader().getAanbieder());
                    message.getMeta().setGemeenteCode(wmo303Bericht.getHeader().getGemeente());
                    break;
                case "WMO305":
                    WMO305Bericht wmo305Bericht = MarshallUtil.unmarshall(message.getBody(), WMO305Bericht.class);
                    message.getMeta().setAgbCode(wmo305Bericht.getHeader().getOntvanger());
                    message.getMeta().setGemeenteCode(wmo305Bericht.getHeader().getAfzender());
                    message.getMeta().setBsn(wmo305Bericht.getClient().getBsn());
                    if (wmo305Bericht.getClient().getStartProducten().getStartProduct().size() > 0) {
                        message.getMeta().setBeschikkingsnummer(wmo305Bericht.getClient().getStartProducten().getStartProduct().get(0).getBeschikkingNummer());
                    }
                    break;
                case "WMO306":
                    WMO306Bericht wmo306Bericht = MarshallUtil.unmarshall(message.getBody(), WMO306Bericht.class);
                    message.getMeta().setAgbCode(wmo306Bericht.getHeader().getOntvanger());
                    message.getMeta().setGemeenteCode(wmo306Bericht.getHeader().getAfzender());
                    message.getMeta().setBsn(wmo306Bericht.getClient().getBsn());
                    if (wmo306Bericht.getClient().getStartProducten().getStartProduct().size() > 0) {
                        message.getMeta().setBeschikkingsnummer(wmo306Bericht.getClient().getStartProducten().getStartProduct().get(0).getBeschikkingNummer());
                    }
                    break;
                case "WMO307":
                    WMO307Bericht wmo307Bericht = MarshallUtil.unmarshall(message.getBody(), WMO307Bericht.class);
                    message.getMeta().setAgbCode(wmo307Bericht.getHeader().getOntvanger());
                    message.getMeta().setGemeenteCode(wmo307Bericht.getHeader().getAfzender());
                    message.getMeta().setBsn(wmo307Bericht.getClient().getBsn());
                    if (wmo307Bericht.getClient().getStopProducten().getStopProduct().size() > 0) {
                        message.getMeta().setBeschikkingsnummer(wmo307Bericht.getClient().getStopProducten().getStopProduct().get(0).getBeschikkingNummer());
                    }
                    break;
                case "WMO308":
                    WMO308Bericht wmo308Bericht = MarshallUtil.unmarshall(message.getBody(), WMO308Bericht.class);
                    message.getMeta().setAgbCode(wmo308Bericht.getHeader().getOntvanger());
                    message.getMeta().setGemeenteCode(wmo308Bericht.getHeader().getAfzender());
                    message.getMeta().setBsn(wmo308Bericht.getClient().getBsn());
                    if (wmo308Bericht.getClient().getStopProducten().getStopProduct().size() > 0) {
                        message.getMeta().setBeschikkingsnummer(wmo308Bericht.getClient().getStopProducten().getStopProduct().get(0).getBeschikkingNummer());
                    }
                    break;
                case "WMO315":
                    WMO315Bericht wmo315Bericht = MarshallUtil.unmarshall(message.getBody(), WMO315Bericht.class);
                    message.getMeta().setAgbCode(wmo315Bericht.getHeader().getAfzender());
                    message.getMeta().setGemeenteCode(wmo315Bericht.getHeader().getOntvanger());
                    message.getMeta().setBsn(wmo315Bericht.getClient().getBsn());
                    if (wmo315Bericht.getClient().getAangevraagdeProducten().getAangevraagdProduct().size() > 0) {
                        message.getMeta().setBeschikkingsnummer(wmo315Bericht.getClient().getAangevraagdeProducten().getAangevraagdProduct().get(0).getBeschikkingNummer());
                    }
                    break;
                case "WMO316":
                    WMO316Bericht wmo316Bericht = MarshallUtil.unmarshall(message.getBody(), WMO316Bericht.class);
                    message.getMeta().setAgbCode(wmo316Bericht.getHeader().getAfzender());
                    message.getMeta().setGemeenteCode(wmo316Bericht.getHeader().getOntvanger());
                    message.getMeta().setBsn(wmo316Bericht.getClient().getBsn());
                    if (wmo316Bericht.getClient().getAangevraagdeProducten().getAangevraagdProduct().size() > 0) {
                        message.getMeta().setBeschikkingsnummer(wmo316Bericht.getClient().getAangevraagdeProducten().getAangevraagdProduct().get(0).getBeschikkingNummer());
                    }
                    break;
            }
        } catch (JAXBException e) {
            log.error("Error while parsing message", e);
            message.getMeta().setError(e.getClass().getSimpleName() + " while parsing message: " + e.getMessage());
        }
    }
}

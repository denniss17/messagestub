package nl.dennisschroer.messagestub.message.ieb.action;

import lombok.Getter;
import nl.dennisschroer.messagestub.MarshallUtil;
import nl.dennisschroer.messagestub.message.Message;
import nl.dennisschroer.messagestub.message.MessageAction;
import nl.dennisschroer.messagestub.message.MessageService;
import nl.istandaarden.generated.ieb.wmo401.WMO401Bericht;
import nl.istandaarden.generated.ieb.wmo402.ObjectFactory;
import nl.istandaarden.generated.ieb.wmo402.WMO402Bericht;
import org.springframework.stereotype.Component;

import javax.xml.datatype.DatatypeFactory;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.Set;

@Component
public class GenerateEigenBijdrageStartRetourMessageAction implements MessageAction {
    private final MessageService messageService;

    @Getter
    private final Set<String> applicableMessageTypes = Collections.singleton("WMO401");

    @Getter
    private final String name = "generateEbStartRetour";

    @Getter
    private final String description = "Genereer een retourbericht (402) op een eigen bijdrage startbericht";

    public GenerateEigenBijdrageStartRetourMessageAction(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public void execute(Message message) throws Exception {
        WMO401Bericht bericht = MarshallUtil.unmarshall(message.getBody(), WMO401Bericht.class);

        ObjectFactory factory = new ObjectFactory();

        WMO402Bericht retourBericht = factory.createWMO402Bericht();
        retourBericht.setHeader(factory.createHeader());
        retourBericht.getHeader().setBerichtCode("471");
        retourBericht.getHeader().setBerichtVersie(1);
        retourBericht.getHeader().setBerichtSubversie(0);
        retourBericht.getHeader().setAfzender(bericht.getHeader().getAfzender());
        retourBericht.getHeader().setBerichtIdentificatie(bericht.getHeader().getBerichtIdentificatie());
        retourBericht.getHeader().setXsdVersie(bericht.getHeader().getXsdVersie());
        retourBericht.getHeader().setIdentificatieRetour(generateIdentificatieRetour(bericht));
        retourBericht.getHeader().setDagtekeningRetour(DatatypeFactory.newInstance().newXMLGregorianCalendar(GregorianCalendar.from(ZonedDateTime.now())));
        retourBericht.getHeader().setXsdVersieRetour(bericht.getHeader().getXsdVersie());

        messageService.saveMessage(new Message("WMO402", MarshallUtil.marshall(retourBericht)));
    }

    private String generateIdentificatieRetour(WMO401Bericht bericht) {
        return bericht.getHeader().getBerichtIdentificatie().getIdentificatie() + "_r";
    }
}

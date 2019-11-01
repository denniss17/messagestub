package nl.dennisschroer.messagestub.message.ieb.action;

import lombok.Getter;
import nl.dennisschroer.messagestub.MarshallUtil;
import nl.dennisschroer.messagestub.message.Message;
import nl.dennisschroer.messagestub.message.action.MessageAction;
import nl.dennisschroer.messagestub.message.action.MessageActionResult;
import nl.dennisschroer.messagestub.message.event.MessageGeneratedEvent;
import nl.istandaarden.generated.ieb.wmo401.WMO401Bericht;
import nl.istandaarden.generated.ieb.wmo402.ObjectFactory;
import nl.istandaarden.generated.ieb.wmo402.WMO402Bericht;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import javax.xml.datatype.DatatypeFactory;
import java.time.ZonedDateTime;
import java.util.GregorianCalendar;

@Component
public class GenerateEigenBijdrageStartRetourMessageAction implements MessageAction {
    private final ApplicationEventPublisher eventPublisher;

    @Getter
    private final String name = "generateEbStartRetour";

    @Getter
    private final String description = "Genereer een retourbericht (402) op een eigen bijdrage startbericht (401)";


    public GenerateEigenBijdrageStartRetourMessageAction(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @Override
    public boolean isApplicableToMessageType(@NotNull String messageType) {
        return messageType.equals("WMO401");
    }

    @Override
    public MessageActionResult execute(Message message) throws Exception {
        WMO401Bericht bericht = MarshallUtil.unmarshall(message.getBody(), WMO401Bericht.class);

        ObjectFactory factory = new ObjectFactory();

        // Maak bericht
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

        // Maak message
        Message result = new Message("WMO402", MarshallUtil.marshall(factory.createBericht(retourBericht)));

        // Vul metadata
        result.getMeta().setBsn(message.getMeta().getBsn());
        result.getMeta().setStartnummer(message.getMeta().getStartnummer());

        eventPublisher.publishEvent(new MessageGeneratedEvent(result));

        MessageActionResult actionResult = new MessageActionResult();
        actionResult.addGeneratedEntity("message", result.getId());
        return actionResult;
    }

    private String generateIdentificatieRetour(WMO401Bericht bericht) {
        return bericht.getHeader().getBerichtIdentificatie().getIdentificatie() + "_r";
    }
}

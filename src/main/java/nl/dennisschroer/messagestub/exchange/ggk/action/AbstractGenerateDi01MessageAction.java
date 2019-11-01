package nl.dennisschroer.messagestub.exchange.ggk.action;

import nl.dennisschroer.messagestub.MarshallUtil;
import nl.dennisschroer.messagestub.exchange.ExchangeMessage;
import nl.dennisschroer.messagestub.exchange.ExchangeMessageService;
import nl.dennisschroer.messagestub.exchange.MessageDirection;
import nl.dennisschroer.messagestub.exchange.ggk.GgkConstants;
import nl.dennisschroer.messagestub.message.Message;
import nl.dennisschroer.messagestub.message.action.MessageAction;
import nl.dennisschroer.messagestub.message.action.MessageActionResult;
import nl.egem.stuf.stuf0301.Berichtcode;
import nl.egem.stuf.stuf0301.Systeem;
import nl.stufstandaarden.koppelvlak.ggk0210.BinaireInhoudBasisGgkberichten;
import nl.stufstandaarden.koppelvlak.ggk0210.EnvelopHeenberichtGgkDi01;
import nl.stufstandaarden.koppelvlak.ggk0210.ObjectFactory;

import javax.xml.bind.JAXBException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.UUID;

/**
 * @author Dennis Schroer
 * @since 01 Nov 2019
 */
public abstract class AbstractGenerateDi01MessageAction implements MessageAction {
    private final ObjectFactory ggkObjectFactory = new ObjectFactory();

    private final nl.egem.stuf.stuf0301.ObjectFactory stufObjectFactory = new nl.egem.stuf.stuf0301.ObjectFactory();

    private final ExchangeMessageService exchangeMessageService;

    protected AbstractGenerateDi01MessageAction(ExchangeMessageService exchangeMessageService) {
        this.exchangeMessageService = exchangeMessageService;
    }

    @Override
    public String getName() {
        return "generateDi01";
    }

    @Override
    public String getDescription() {
        return "Genereer een Di01 om dit bericht via de GGK-koppeling te versturen";
    }

    @Override
    public MessageActionResult execute(Message message) throws JAXBException {
        EnvelopHeenberichtGgkDi01 di01 = ggkObjectFactory.createEnvelopHeenberichtGgkDi01();

        di01.setStuurgegevens(stufObjectFactory.createStuurgegevensDi01EnvelopHeenbericht());
        di01.getStuurgegevens().setBerichtcode(Berichtcode.DI_01);
        di01.getStuurgegevens().setZender(createZender());
        di01.getStuurgegevens().setOntvanger(createOntvanger(message));
        di01.getStuurgegevens().setReferentienummer(UUID.randomUUID().toString());
        di01.getStuurgegevens().setTijdstipBericht(GgkConstants.DATE_TIME_FORMATTER.format(Instant.now()));
        di01.getStuurgegevens().setFunctie(getBerichtFunctie(message));

        di01.setParameters(ggkObjectFactory.createParametersGgkberichten());
        di01.getParameters().setApplicatieVersie(GgkConstants.APPLICATIE_VERSIE);
        di01.getParameters().setApplicatieSubversie(GgkConstants.APPLICATIE_SUBVERSIE);
        di01.getParameters().setFunctieVersie(getFunctieVersie());
        di01.getParameters().setFunctieSubversie(getFunctieSubversie());
        di01.getParameters().setBericht(createBericht(message));

        ExchangeMessage exchangeMessage = new ExchangeMessage("Di01", message.getType(), MessageDirection.OUT);
        exchangeMessage.setBody(MarshallUtil.marshall(di01));
        exchangeMessage.setMessage(message);
        exchangeMessage = exchangeMessageService.saveExchangeMessage(exchangeMessage);

        return new MessageActionResult(exchangeMessage);
    }

    protected String getBerichtFunctie(Message message) {
        return message.getType();
    }

    /**
     * Geeft de versie die moet worden meegestuurd als functieVersie. Dit moet een string van 4 karakters zijn.
     */
    protected abstract String getFunctieVersie();

    /**
     * Geeft de versie die moet worden meegestuurd als functieSubversie. Dit moet een string van 4 karakters zijn.
     */
    protected abstract String getFunctieSubversie();

    /**
     * De ontvangende organisatie.
     */
    protected String getOntvangerOrganisatie(Message message) {
        return message.getMeta().getGemeenteCode();
    }

    /**
     * De ontvangende applicatie
     */
    protected String getOntvangerApplicatie(Message message) {
        return message.getMeta().getApplicatieNaam();
    }

    private BinaireInhoudBasisGgkberichten createBericht(Message message) {
        BinaireInhoudBasisGgkberichten bericht = ggkObjectFactory.createBinaireInhoudBasisGgkberichten();
        bericht.setXmlBestand(message.getBody().getBytes(StandardCharsets.UTF_8));
        return bericht;
    }

    private Systeem createZender() {
        Systeem zender = stufObjectFactory.createSysteem();
        zender.setApplicatie(GgkConstants.DEFAULT_ZENDER_APPLICATIE);
        zender.setOrganisatie(GgkConstants.DEFAULT_ZENDER_ORGANISATIE);
        return zender;
    }

    private Systeem createOntvanger(Message message) {
        Systeem ontvanger = stufObjectFactory.createSysteem();
        ontvanger.setApplicatie(getOntvangerApplicatie(message));
        ontvanger.setOrganisatie(getOntvangerOrganisatie(message));
        return ontvanger;
    }
}

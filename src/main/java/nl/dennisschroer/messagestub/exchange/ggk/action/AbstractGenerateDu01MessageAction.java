package nl.dennisschroer.messagestub.exchange.ggk.action;

import lombok.extern.apachecommons.CommonsLog;
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
import nl.stufstandaarden.koppelvlak.ggk0210.EnvelopRetourberichtGgkDu01;
import nl.stufstandaarden.koppelvlak.ggk0210.ObjectFactory;

import javax.xml.bind.JAXBException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @author Dennis Schroer
 * @since 01 Nov 2019
 */
@CommonsLog
public abstract class AbstractGenerateDu01MessageAction implements MessageAction {
    private final ObjectFactory ggkObjectFactory = new ObjectFactory();

    private final nl.egem.stuf.stuf0301.ObjectFactory stufObjectFactory = new nl.egem.stuf.stuf0301.ObjectFactory();

    private final ExchangeMessageService exchangeMessageService;

    protected AbstractGenerateDu01MessageAction(ExchangeMessageService exchangeMessageService) {
        this.exchangeMessageService = exchangeMessageService;
    }

    @Override
    public String getName() {
        return "generateDu01";
    }

    @Override
    public String getDescription() {
        return "Genereer een Du01 om dit bericht via de GGK-koppeling te versturen";
    }

    @Override
    public MessageActionResult execute(Message message) throws JAXBException {
        EnvelopRetourberichtGgkDu01 du01 = ggkObjectFactory.createEnvelopRetourberichtGgkDu01();

        du01.setStuurgegevens(stufObjectFactory.createStuurgegevensDu01EnvelopRetourbericht());
        du01.getStuurgegevens().setBerichtcode(Berichtcode.DU_01);
        du01.getStuurgegevens().setZender(createZender());
        du01.getStuurgegevens().setOntvanger(createOntvanger(message));
        du01.getStuurgegevens().setReferentienummer(UUID.randomUUID().toString());
        du01.getStuurgegevens().setCrossRefnummer(message.getMeta().getReferentienummer());
        du01.getStuurgegevens().setTijdstipBericht(GgkConstants.DATE_TIME_FORMATTER.format(LocalDateTime.now()));
        du01.getStuurgegevens().setFunctie(getBerichtFunctie(message));

        du01.setParameters(ggkObjectFactory.createParametersGgkberichten());
        du01.getParameters().setApplicatieVersie(GgkConstants.APPLICATIE_VERSIE);
        du01.getParameters().setApplicatieSubversie(GgkConstants.APPLICATIE_SUBVERSIE);
        du01.getParameters().setFunctieVersie(getFunctieVersie());
        du01.getParameters().setFunctieSubversie(getFunctieSubversie());
        du01.getParameters().setBericht(createBericht(message));

        ExchangeMessage exchangeMessage = new ExchangeMessage("GGK", "Du01", MessageDirection.OUT);
        exchangeMessage.setBody(MarshallUtil.marshall(du01));
        exchangeMessage.setMessage(message);
        exchangeMessage = exchangeMessageService.saveExchangeMessage(exchangeMessage);

        log.info("GGK: Du01 gegenereerd: " + exchangeMessage);

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

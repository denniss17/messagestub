package nl.dennisschroer.messagestub.exchange.ggk;

import nl.egem.stuf.stuf0301.Berichtcode;
import nl.egem.stuf.stuf0301.Bv03Bericht;
import nl.egem.stuf.stuf0301.Fo01Bericht;
import nl.egem.stuf.stuf0301.ObjectFactory;
import nl.egem.stuf.stuf0301.Stuurgegevens;
import nl.egem.stuf.stuf0301.StuurgegevensDi01EnvelopHeenbericht;
import nl.egem.stuf.stuf0301.Systeem;
import nl.stufstandaarden.koppelvlak.ggk0210.EnvelopHeenberichtGgkDi01;
import nl.stufstandaarden.koppelvlak.ggk0210.EnvelopRetourberichtGgkDu01;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class GgkResponseGeneratorImpl implements GgkResponseGenerator {

    private static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    private final ObjectFactory stufObjectFactory = new ObjectFactory();

    @Override
    public Bv03Bericht generateResponse(EnvelopHeenberichtGgkDi01 bericht) {
        Bv03Bericht bv03Bericht = stufObjectFactory.createBv03Bericht();
        bv03Bericht.setStuurgegevens(createBv03BerichtStuurgegevens(bericht.getStuurgegevens()));
        return bv03Bericht;
    }

    @Override
    public Bv03Bericht generateResponse(EnvelopRetourberichtGgkDu01 retourBericht) {
        Bv03Bericht bv03Bericht = stufObjectFactory.createBv03Bericht();
        bv03Bericht.setStuurgegevens(createBv03BerichtStuurgegevens(retourBericht.getStuurgegevens()));
        return bv03Bericht;
    }

    @Override
    public Bv03Bericht generateResponse(Fo01Bericht foutBericht) {
        Bv03Bericht bv03Bericht = stufObjectFactory.createBv03Bericht();
        bv03Bericht.setStuurgegevens(createBv03BerichtStuurgegevens(foutBericht.getStuurgegevens()));
        return bv03Bericht;
    }

    private Bv03Bericht.Stuurgegevens createBv03BerichtStuurgegevens(Stuurgegevens requestStuurgegevens) {
        Bv03Bericht.Stuurgegevens result = stufObjectFactory.createBv03BerichtStuurgegevens();
        result.setBerichtcode(Berichtcode.BV_03);
        result.setZender(requestStuurgegevens.getOntvanger());
        result.setOntvanger(requestStuurgegevens.getZender());
        result.setReferentienummer(UUID.randomUUID().toString());
        result.setTijdstipBericht(LocalDateTime.now().format(DATE_TIME_FORMATTER));
        result.setCrossRefnummer(requestStuurgegevens.getReferentienummer());
        return result;
    }
}

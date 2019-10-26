package nl.dennisschroer.messagestub.exchange.ggk;

import nl.egem.stuf.stuf0301.Bv03Bericht;
import nl.egem.stuf.stuf0301.Fo01Bericht;
import nl.stufstandaarden.koppelvlak.ggk0210.EnvelopHeenberichtGgkDi01;
import nl.stufstandaarden.koppelvlak.ggk0210.EnvelopRetourberichtGgkDu01;

public interface GgkResponseGenerator {
    Bv03Bericht generateResponse(EnvelopHeenberichtGgkDi01 bericht);

    Bv03Bericht generateResponse(EnvelopRetourberichtGgkDu01 retourBericht);

    Bv03Bericht generateResponse(Fo01Bericht foutBericht);
}

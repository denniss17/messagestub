package nl.dennisschroer.messagestub.exchange.ggk;

import java.time.format.DateTimeFormatter;

/**
 * @author Dennis Schroer
 * @since 01 Nov 2019
 */
public class GgkConstants {
    public static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    public static final String SOAP_ACTION_DI01 = "http://www.stufstandaarden.nl/koppelvlak/ggk0210/ggk_Di01";

    public static final String SOAP_ACTION_DU01 = "http://www.stufstandaarden.nl/koppelvlak/ggk0210/ggk_Du01";

    public static final String SOAP_ACTION_FO01 = "http://www.egem.nl/StUF/StUF0301/Fo01";

    public static final String DEFAULT_ZENDER_APPLICATIE = "messagestub";

    public static final String DEFAULT_ZENDER_ORGANISATIE = "000000000";

    public static final String APPLICATIE_VERSIE = "0001";

    public static final String APPLICATIE_SUBVERSIE = "0001";
}

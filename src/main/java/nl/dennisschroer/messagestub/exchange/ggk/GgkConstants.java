package nl.dennisschroer.messagestub.exchange.ggk;

import java.time.format.DateTimeFormatter;

/**
 * @author Dennis Schroer
 * @since 01 Nov 2019
 */
public class GgkConstants {
    public static final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);

    public static final String DEFAULT_ZENDER_APPLICATIE = "messagestub";

    public static final String DEFAULT_ZENDER_ORGANISATIE = "000000000";

    public static final String APPLICATIE_VERSIE = "0001";

    public static final String APPLICATIE_SUBVERSIE = "0001";
}

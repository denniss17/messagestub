package nl.dennisschroer.messagestub.message;

import lombok.Data;
import nl.dennisschroer.messagestub.exchange.ExchangeMessage;

/**
 * Event wat aangeeft dat er een message ontvangen is via één van de exchanges.
 * <p>
 * Ontvangers kunnen op basis van dit event bepalen of zij kaas van de inhoud kunnen maken.
 */
@Data
public class MessageReceivedEvent {
    /**
     * De message van de exchange (bijv. GGK) waarin dit bericht zat.
     * <p>
     * Dit kan gebruikt worden om het ontvangen bericht te relateren aan het bericht van de exchange, bijvoorbeeld
     * een WMO301 bericht aan de GGK Di01 waarin deze zat.
     */
    private final ExchangeMessage exchangeMessage;

    /**
     * Het type van het bericht, gedetecteerd door de exchange.
     */
    private final String messageType;

    /**
     * De inhoud van het bericht.
     */
    private final String body;
}

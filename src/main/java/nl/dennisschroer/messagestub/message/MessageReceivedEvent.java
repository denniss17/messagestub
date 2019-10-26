package nl.dennisschroer.messagestub.message;

import lombok.Data;

/**
 * Event wat aangeeft dat er een message ontvangen is via één van de exchanges.
 *
 * Ontvangers kunnen op basis van dit event bepalen of zij kaas van de inhoud kunnen maken.
 */
@Data
public class MessageReceivedEvent {
    private final String messageType;

    private final String body;
}

package nl.dennisschroer.messagestub.message.event;

import nl.dennisschroer.messagestub.message.Message;

/**
 * Interface voor een event wat aangeeft dat er een nieuwe {@link Message} is.
 * <p>
 * Ontvangers kunnen op basis van dit event bepalen of zij kaas van de inhoud kunnen maken en op deze manier de metainformatie
 * van de message kunnen verrijken.
 */
public interface NewMessageEvent {
    Message getMessage();
}

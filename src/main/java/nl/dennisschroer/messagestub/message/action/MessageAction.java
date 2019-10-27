package nl.dennisschroer.messagestub.message.action;

import nl.dennisschroer.messagestub.message.Message;

import java.util.Set;

/**
 * Een actie die op een bepaald type bericht of types berichten kan worden uitgevoerd.
 * <p>
 * Voorbeeld: het genereren van een retourbericht (WMO302) bij een WMO301.
 */
public interface MessageAction {
    /**
     * Types van berichten waarop deze actie toepasbaar is.
     */
    Set<String> getApplicableMessageTypes();

    /**
     * De naam van deze actie.
     */
    String getName();

    /**
     * Een korte uitleg over deze actie.
     */
    String getDescription();

    /**
     * Voer deze actie uit over een {@link Message}.
     */
    MessageActionResult execute(Message message) throws Exception;
}

package nl.dennisschroer.messagestub.message;

import javax.xml.bind.JAXBException;
import javax.xml.datatype.DatatypeConfigurationException;
import java.util.Set;

/**
 * Een actie die op een bepaald type bericht of types berichten kan worden uitgevoerd.
 *
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
    void execute(Message message) throws Exception;
}

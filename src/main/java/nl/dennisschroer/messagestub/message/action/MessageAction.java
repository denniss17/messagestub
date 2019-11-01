package nl.dennisschroer.messagestub.message.action;

import nl.dennisschroer.messagestub.message.Message;

import javax.validation.constraints.NotNull;

/**
 * Op en ontvangen of gegenereerd bericht kan een actie worden uitgevoerd. Dit kan varieren van het verzenden van dit bericht
 * via een bepaalde exchange tot het genereren van een response op dit bericht.
 * <p>
 * Een {@link MessageAction} definieert zo'n actie. Een actie is alleen toepasbaar op een berichtbype,
 * als {@link MessageAction#isApplicableToMessageType(String)} {@code true} terug geeft.
 * <p>
 * Een actie die op een bepaald type bericht of types berichten kan worden uitgevoerd.
 * <p>
 * Voorbeeld: het genereren van een retourbericht (WMO302) bij een WMO301.
 */
public interface MessageAction {
    /**
     * Geeft terug of deze actie toepasbaar is op het gegeven message type.
     */
    boolean isApplicableToMessageType(@NotNull String messageType);

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

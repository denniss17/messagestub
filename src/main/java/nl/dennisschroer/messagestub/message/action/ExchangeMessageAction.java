package nl.dennisschroer.messagestub.message.action;

import nl.dennisschroer.messagestub.exchange.ExchangeMessage;

import javax.validation.constraints.NotNull;

/**
 * Op en ontvangen of gegenereerd {@link ExchangeMessage} kan een actie worden uitgevoerd. Dit kan varieren van het verzenden van dit bericht
 * via een bepaalde exchange tot het genereren van een response op dit bericht.
 * <p>
 * Een {@link ExchangeMessageAction} definieert zo'n actie. Een actie is alleen toepasbaar op een berichtbype,
 * als {@link ExchangeMessageAction#isApplicableToMessageType(String)} {@code true} terug geeft.
 * <p>
 * Een actie die op een bepaald type bericht of types berichten kan worden uitgevoerd.
 * <p>
 * Voorbeeld: het versturen van een Du01 via een GGK-koppeling.
 */
public interface ExchangeMessageAction {
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
     * Voer deze actie uit over een {@link ExchangeMessage}.
     */
    MessageActionResult execute(ExchangeMessage message) throws Exception;
}

package nl.dennisschroer.messagestub.exchange.ggk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.dennisschroer.messagestub.exchange.ExchangeMessage;
import nl.dennisschroer.messagestub.message.Message;
import nl.egem.stuf.stuf0301.Stuurgegevens;

/**
 * Enum of all the "Functies" which can be exchanged through the GGK-exchange. Each value also specifies the message
 * type to use for the wrapped {@link Message} in the {@link ExchangeMessage}.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum GgkBerichtFunctie {
    Fo01,
    WMO301("WMO301"),
    WMO302("WMO302"),
    WMO303("WMO303"),
    WMO304("WMO304"),
    WMO305("WMO305"),
    WMO306("WMO306"),
    WMO307("WMO307"),
    WMO308("WMO308"),
    WMO315("WMO315"),
    WMO316("WMO316"),
    JW301("JW301"),
    JW302("JW302"),
    JW303("JW303"),
    JW304("JW304"),
    JW305("JW305"),
    JW306("JW306"),
    JW307("JW307"),
    JW308("JW308"),
    JW315("JW315"),
    JW316("JW316"),
    WMO401("WMO401"),
    WMO402("WMO402"),
    WMO403("WMO403"),
    WMO404("WMO404");

    /**
     * Het messagetype van de message in de GGK-envelop als deze functie is meegestuurt.
     *
     * @see Stuurgegevens#getFunctie()
     * @see Message#getType()
     */
    private String messageType;
}

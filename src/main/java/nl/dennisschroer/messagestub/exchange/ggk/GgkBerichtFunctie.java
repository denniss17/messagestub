package nl.dennisschroer.messagestub.exchange.ggk;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nl.dennisschroer.messagestub.message.MessageReceivedEvent;

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
    JW316("JW316");

    /**
     * Het messagetype waarme de inhoud van de GGK-envelop gepubliseerd moet worden met een {@link nl.dennisschroer.messagestub.message.MessageReceivedEvent}.
     *
     * @see MessageReceivedEvent#getMessageType()
     */
    private String messageType;
}

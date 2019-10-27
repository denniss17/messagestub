package nl.dennisschroer.messagestub.exchange;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import nl.dennisschroer.messagestub.MessageDirection;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Een bericht die via een exchange (bijvoorbeeld GGK) is ontvangen danwel verstuurd.
 */
@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class ExchangeMessage {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Identificatie van de exchange (bijv GGK) via welke dit bericht is ontvangen of verstuurd.
     */
    @NotNull
    private String exchangeType;

    /**
     * Het type bericht binnen de exchange wat is ontvangen of verstuurd, bijvoorbeeld "Di01" of "Fo03"
     */
    @NotNull
    private String messageType;

    /**
     * Geeft aan of het om een ontvangen of verzonden bericht gaat.
     */
    @NotNull
    @Enumerated(EnumType.STRING)
    private MessageDirection direction;

    /**
     * Het adres van de andere partij.
     * <p>
     * In het geval van een incoming bericht: het ipadres van de client
     * In het geval van een uitgaand bericht: het adres waar het bericht heen is gestuurd.
     */
    private String peerUrl;

    /**
     * De raw content van het bericht, bijvoorbeeld XML.
     */
    @Column(length = 16384)
    private String body;

    /**
     * Als dit bericht een response is op een andere: het bericht waar dit een response op is.
     */
    @ManyToOne
    private ExchangeMessage requestMessage;

    @CreatedDate
    private Date timestamp;

    public ExchangeMessage(String exchangeType, String messageType, MessageDirection direction) {
        this.exchangeType = exchangeType;
        this.messageType = messageType;
        this.direction = direction;
    }
}

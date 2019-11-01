package nl.dennisschroer.messagestub.message;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import nl.dennisschroer.messagestub.exchange.ExchangeMessage;
import nl.dennisschroer.messagestub.message.event.NewMessageEvent;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.Nullable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Data
@Entity
@ToString(exclude = "exchangeMessages")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@EntityListeners(AuditingEntityListener.class)
public class Message {
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Het type van het bericht, gedetecteerd door de exchange.
     */
    @NotNull
    private String type;

    /**
     * Metainformatie die door listeners voor een {@link NewMessageEvent} gevuld kan worden met extra informatie die
     * is afgeleid uit de body.
     */
    @NotNull
    @Embedded
    private MessageMeta meta = new MessageMeta();

    @CreatedDate
    private Date timestamp;

    /**
     * De inhoud van het bericht.
     */
    @NotNull
    @Column(length = 16384)
    private String body;

    /**
     * Exchangemessages waarin dit bericht zich bevindt.
     * <p>
     * Dit kan gebruikt worden om het ontvangen bericht te relateren aan het bericht van de exchange, bijvoorbeeld
     * een WMO301 bericht aan de GGK Di01 waarin deze zat.
     */
    @JsonIgnore
    @OneToMany(mappedBy = "message")
    private List<ExchangeMessage> exchangeMessages;

    public Message(String type, String body) {
        this.type = type;
        this.body = body;
    }

    @Data
    @Embeddable
    public static class MessageMeta {
        @Nullable
        private String bsn;

        @Nullable
        private String agbCode;

        @Nullable
        private String gemeenteCode;

        @Nullable
        private String applicatieNaam;

        @Nullable
        private Integer beschikkingsnummer;

        @Nullable
        private String referentienummer;

        /**
         * Startnummer uit iEb (eigen bijdrage).
         */
        @Nullable
        private Integer startnummer;

        /**
         * Error message when there is something wrong with the message.
         */
        @Nullable
        private String error;
    }
}

package nl.dennisschroer.messagestub.exchange;

import lombok.Data;
import nl.dennisschroer.messagestub.MessageDirection;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.lang.NonNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Een bericht die via een exchange (bijvoorbeeld GGK) is ontvangen danwel verstuurd.
 */
@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class ExchangeMessage {
    @Id
    @GeneratedValue
    private Long id;

    @NonNull
    private String exchangeType;

    @NonNull
    private String messageType;

    @NonNull
    @Enumerated(EnumType.STRING)
    private MessageDirection direction;

    @Column(length = 2048)
    private String body;

    /**
     * If this exchange message is a response: the request message to which this message is a response.
     */
    @ManyToOne
    private ExchangeMessage requestMessage;

    @CreatedDate
    private Date timestamp;
}

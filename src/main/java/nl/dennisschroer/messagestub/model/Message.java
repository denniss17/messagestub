package nl.dennisschroer.messagestub.model;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Message {
    public static final String INPUT_REST = "rest";
    public static final String INPUT_SOAP = "soap";

    @Id
    @GeneratedValue
    private UUID id;

    /**
     * Detected type of this message. Types are detected using {@link nl.dennisschroer.messagestub.matcher.TypeMatcher}s.
     */
    private String type;

    /**
     * Input connection through which this message was received.
     */
    private String input;

    /**
     * Path of
     */
    private String path;

    @CreatedDate
    private Date timestamp;

    private String data;
}

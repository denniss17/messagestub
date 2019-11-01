package nl.dennisschroer.messagestub.representation.action;

import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

/**
 * @author Dennis Schroer
 * @since 01 Nov 2019
 */
@Getter
@Setter
public class MessageActionResultRepresentation extends RepresentationModel<MessageActionResultRepresentation> {
    private boolean success;

    private String error;
}

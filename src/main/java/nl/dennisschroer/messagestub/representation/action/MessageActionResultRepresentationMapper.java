package nl.dennisschroer.messagestub.representation.action;

import nl.dennisschroer.messagestub.message.action.MessageActionResult;
import nl.dennisschroer.messagestub.representation.action.MessageActionResultRepresentation;

/**
 * @author Dennis Schroer
 * @since 01 Nov 2019
 */
public interface MessageActionResultRepresentationMapper {
    MessageActionResultRepresentation toRepresentation(MessageActionResult messageActionResult);
}

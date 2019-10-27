package nl.dennisschroer.messagestub.message.action;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MessageActionResult {
    @Getter
    private List<GeneratedEntityReference> generatedEntities = new ArrayList<>();

    public void addGeneratedEntity(String type, Long id) {
        this.generatedEntities.add(new GeneratedEntityReference(type, id));
    }

    @Data
    static class GeneratedEntityReference {
        private final String type;
        private final Long id;
    }
}

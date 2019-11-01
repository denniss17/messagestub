package nl.dennisschroer.messagestub.message.action;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class MessageActionResult {
    @Getter
    private List<Object> generatedEntities = new ArrayList<>();

    public MessageActionResult(Object generatedEntity) {
        this.generatedEntities.add(generatedEntity);
    }

    public void addGeneratedEntity(Object entity) {
        this.generatedEntities.add(entity);
    }
}

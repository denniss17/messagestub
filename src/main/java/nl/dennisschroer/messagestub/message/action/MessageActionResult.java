package nl.dennisschroer.messagestub.message.action;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class MessageActionResult {
    private List<Object> generatedEntities = new ArrayList<>();

    private String error;

    public MessageActionResult(Object generatedEntity) {
        this.generatedEntities.add(generatedEntity);
    }

    public boolean isSuccess() {
        return error == null;
    }
}

package nl.dennisschroer.messagestub.exchange.ggk;

import lombok.Data;
import nl.egem.stuf.stuf0301.Stuurgegevens;
import org.springframework.lang.Nullable;

@Data
public class GgkException extends Exception {
    @Nullable
    private Stuurgegevens stuurgegevens;

    public GgkException(Exception e) {
        super("GgkException: " + e.getMessage(), e);
    }

    public GgkException(Stuurgegevens stuurgegevens, Exception e) {
        this(e);
        this.stuurgegevens = stuurgegevens;
    }
}

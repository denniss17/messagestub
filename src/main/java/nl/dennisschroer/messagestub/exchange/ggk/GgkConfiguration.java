package nl.dennisschroer.messagestub.exchange.ggk;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Dennis Schroer
 * @since 01 Nov 2019
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "ggk")
public class GgkConfiguration {
    private String endpoint;
}

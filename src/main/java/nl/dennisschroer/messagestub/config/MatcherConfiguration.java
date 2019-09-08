package nl.dennisschroer.messagestub.config;

import nl.dennisschroer.messagestub.matcher.PathRegexRequestMatcher;
import nl.dennisschroer.messagestub.matcher.RequestMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class MatcherConfiguration {
    @Bean
    public List<RequestMatcher> typeMatchers() {
        // List for testing. Should be configurable
        return Collections.singletonList(
                new PathRegexRequestMatcher("test\\d*", "test")
        );
    }
}

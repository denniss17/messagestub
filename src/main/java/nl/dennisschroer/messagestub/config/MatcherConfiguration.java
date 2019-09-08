package nl.dennisschroer.messagestub.config;

import nl.dennisschroer.messagestub.matcher.PathRegexTypeMatcher;
import nl.dennisschroer.messagestub.matcher.TypeMatcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class MatcherConfiguration {
    @Bean
    public List<TypeMatcher> typeMatchers() {
        // List for testing. Should be configurable
        return Collections.singletonList(
                new PathRegexTypeMatcher("test\\d*", "test")
        );
    }
}

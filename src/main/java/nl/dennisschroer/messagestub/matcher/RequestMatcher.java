package nl.dennisschroer.messagestub.matcher;

import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;

public interface RequestMatcher {
    @Nullable
    String match(String path, String body, HttpServletRequest request);
}

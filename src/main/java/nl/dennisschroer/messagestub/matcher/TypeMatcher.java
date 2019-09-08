package nl.dennisschroer.messagestub.matcher;

import org.springframework.lang.Nullable;

import javax.servlet.http.HttpServletRequest;

public interface TypeMatcher {
    @Nullable
    String match(String path, String content, HttpServletRequest request);
}

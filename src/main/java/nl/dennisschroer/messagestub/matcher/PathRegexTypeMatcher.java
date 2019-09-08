package nl.dennisschroer.messagestub.matcher;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

public class PathRegexTypeMatcher implements TypeMatcher {

    private final String regex;
    private final String type;

    public PathRegexTypeMatcher(String regex, String type) {
        this.regex = regex;
        this.type = type;
    }

    @Override
    public String match(String path, String content, HttpServletRequest request) {
        return path.matches(regex) ? type : null;
    }
}

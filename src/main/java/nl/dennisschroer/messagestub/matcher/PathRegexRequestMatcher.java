package nl.dennisschroer.messagestub.matcher;

import javax.servlet.http.HttpServletRequest;

public class PathRegexRequestMatcher implements RequestMatcher {

    private final String regex;
    private final String type;

    public PathRegexRequestMatcher(String regex, String type) {
        this.regex = regex;
        this.type = type;
    }

    @Override
    public String match(String path, String body, HttpServletRequest request) {
        return path.matches(regex) ? type : null;
    }
}

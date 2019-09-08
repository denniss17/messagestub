package nl.dennisschroer.messagestub.stub.rest;

import lombok.extern.apachecommons.CommonsLog;
import nl.dennisschroer.messagestub.config.MatcherConfiguration;
import nl.dennisschroer.messagestub.matcher.RequestMatcher;
import nl.dennisschroer.messagestub.model.Message;
import nl.dennisschroer.messagestub.service.MessageService;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Priority;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;

@Controller
@CommonsLog
@RequestMapping("/")
public class RestStubController {
    private final MessageService messageService;
    private final MatcherConfiguration matcherConfiguration;

    public RestStubController(MessageService messageService, MatcherConfiguration matcherConfiguration){
        this.messageService = messageService;
        this.matcherConfiguration = matcherConfiguration;
    }

    @RequestMapping(path = "/{path:.+}")
    public void receive(@RequestBody String body, @PathVariable String path, HttpServletRequest request) {
        // Create message
        Message message = new Message();
        message.setPath(path);
        message.setMethod(request.getMethod());
        message.setBody(body);

        // Detect type using request matchers
        String type = null;
        Iterator<RequestMatcher> iterator = matcherConfiguration.typeMatchers().iterator();
        while(type == null && iterator.hasNext()){
            type = iterator.next().match(path, body, request);
        }
        message.setType(type);

        // Save
        message = messageService.saveMessage(message);

        log.info(String.format("Received message %s on %s with detected type %s", message.getId(), path, type));
    }
}

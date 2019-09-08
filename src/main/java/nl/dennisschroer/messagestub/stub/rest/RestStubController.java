package nl.dennisschroer.messagestub.stub.rest;

import lombok.extern.apachecommons.CommonsLog;
import nl.dennisschroer.messagestub.model.Message;
import nl.dennisschroer.messagestub.service.MessageService;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;

@Controller
@CommonsLog
@RequestMapping("/stub")
public class RestStubController {
    private final MessageService messageService;

    public RestStubController(MessageService messageService){
        this.messageService = messageService;
    }

    @RequestMapping(path = "/{path:.+}", method = RequestMethod.POST)
    public void receive(@RequestBody String data, @PathVariable String path, HttpServletRequest request) {
        Message message = new Message();
        message.setData(data);
        message = messageService.saveMessage(message);

        //request

        log.info(String.format("Received message %s on /stub/%s", message.getId(), path));
    }
}

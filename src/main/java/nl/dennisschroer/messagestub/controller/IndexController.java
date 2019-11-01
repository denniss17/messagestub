package nl.dennisschroer.messagestub.controller;

import lombok.extern.apachecommons.CommonsLog;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@CommonsLog
@RequestMapping("/api")
public class IndexController {
    @ResponseBody
    @GetMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public RepresentationModel getMessages() {
        RepresentationModel result = new RepresentationModel();

        result.add(linkTo(methodOn(MessageController.class).getMessages()).withRel("messages"));
        result.add(linkTo(methodOn(ExchangeMessageController.class).getExchangeMessages()).withRel("exchangeMessages"));

        return result;
    }
}

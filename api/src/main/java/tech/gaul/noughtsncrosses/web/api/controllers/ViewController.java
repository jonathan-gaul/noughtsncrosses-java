package tech.gaul.noughtsncrosses.web.api.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

    @RequestMapping({ "/play" })
    public String index() {
        // Support deep linking for Angular
        return "forward:/index.html";
    }
}

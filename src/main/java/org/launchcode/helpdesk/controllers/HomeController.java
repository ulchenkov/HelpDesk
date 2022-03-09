package org.launchcode.helpdesk.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping
    public String index(Model model) {
        model.addAttribute("title","The first string");
        String[] footers = {"footer1", "footer2"};
        model.addAttribute("footers", footers);
        return "index";
    }
}

package org.launchcode.helpdesk.controllers;

import org.launchcode.helpdesk.helpers.SDHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController extends AbstractBaseController {

    private String basePath="/";

    @GetMapping
    public String index(Model model) {
        SDHelper.initializeModel(model, this.basePath, "", "index-page");
        return "index";
    }

}

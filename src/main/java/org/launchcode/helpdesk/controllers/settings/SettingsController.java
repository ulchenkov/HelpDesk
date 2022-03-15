package org.launchcode.helpdesk.controllers.settings;

import org.launchcode.helpdesk.helpers.SDHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("settings")
public class SettingsController {

    private final String basePath = "/settings/";

    @GetMapping
    public String index (Model model) {
        SDHelper.initializeModel(model,this.basePath, "", "main");
        return "index";
    }
}

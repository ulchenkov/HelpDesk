package org.launchcode.helpdesk.controllers.settings;

import org.launchcode.helpdesk.data.helpers.Link;
import org.launchcode.helpdesk.data.helpers.LinkList;
import org.launchcode.helpdesk.data.helpers.Fragment;
import org.launchcode.helpdesk.data.helpers.SDHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("settings")
public class SettingsController {

    private final String fragmentFile = "settings/fragments";
    private final String basePath = "/settings/";

    final static LinkList settingsMenu = new LinkList();
    final static String settingsMenuTitle = "Settings";

    public SettingsController() {
        settingsMenu.add(new Link("Users", this.basePath + "users/"));
        settingsMenu.add(new Link("Groups", this.basePath + "groups/"));
        settingsMenu.add(new Link("Departments", this.basePath + "departments/"));
        settingsMenu.add(new Link("Ticket Categories", this.basePath + "categories/"));
        settingsMenu.add(new Link("Ticket Priorities", this.basePath + "priorities/"));
    }

    @GetMapping
    public String index (Model model) {

        LinkList crumbs= SDHelper.getBreadCrumbs(this.basePath);
        model.addAttribute("breadCrumbs", crumbs.getAll());
        model.addAttribute("title",crumbs.getLast().getText());

        model.addAttribute(
                "content",
                new Fragment[] {new Fragment(this.fragmentFile, "main")});

        model.addAttribute("leftMenu", settingsMenu.getAll());
        model.addAttribute("leftMenuTitle", settingsMenuTitle);

        return "index";
    }
}

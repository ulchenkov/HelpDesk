package org.launchcode.helpdesk.controllers.settings;

import org.launchcode.helpdesk.data.helpers.LinkList;
import org.launchcode.helpdesk.data.helpers.Fragment;
import org.launchcode.helpdesk.data.helpers.SDHelper;
import org.launchcode.helpdesk.data.UserRepository;
import org.launchcode.helpdesk.data.dto.UserDto;
import org.launchcode.helpdesk.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("settings/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final String fragmentFile = "settings/users/fragments";
    private final String basePath = "/settings/users/";

    @GetMapping
    public String index(Model model) {

        LinkList crumbs= SDHelper.getBreadCrumbs(this.basePath);
        model.addAttribute("breadCrumbs", crumbs.getAll());
        model.addAttribute("title",crumbs.getLast().getText());

        model.addAttribute("addLink", crumbs.getLast().getUrl() + "add/");
        model.addAttribute(
                "content",
                new Fragment[] {new Fragment(this.fragmentFile, "main-table")});
        model.addAttribute("leftMenu", SettingsController.settingsMenu.getAll());
        model.addAttribute("leftMenuTitle", SettingsController.settingsMenuTitle);

        model.addAttribute("users", userRepository.findAll());

        return "index";
    }

    @GetMapping("add")
    public String displayAddForm(Model model) {

        LinkList crumbs = SDHelper.getBreadCrumbs(this.basePath + "add/");
        model.addAttribute("breadCrumbs", crumbs.getAll());
        model.addAttribute("title",crumbs.getLast().getText());

        model.addAttribute(
                "content",
                new Fragment[] {new Fragment(this.fragmentFile, "add-user")});
        model.addAttribute("leftMenu", SettingsController.settingsMenu.getAll());
        model.addAttribute("leftMenuTitle", SettingsController.settingsMenuTitle);

        model.addAttribute("userDto", new UserDto());

        return "index";
    }

    @PostMapping("add")
    public String processAddForm(
            @ModelAttribute @Valid UserDto userDto,
            Errors errors,
            Model model) {

        LinkList crumbs= SDHelper.getBreadCrumbs(this.basePath + "add/");
        model.addAttribute("breadCrumbs", crumbs.getAll());
        model.addAttribute("title",crumbs.getLast().getText());

        model.addAttribute(
                "content",
                new Fragment[] {new Fragment(this.fragmentFile, "add-user")});
        model.addAttribute("leftMenu", SettingsController.settingsMenu.getAll());
        model.addAttribute("leftMenuTitle", SettingsController.settingsMenuTitle);

        if (errors.hasErrors()) {
            return "index";
        }
        try {
            userRepository.save(new User(userDto));
        } catch (DataIntegrityViolationException exception) {
            model.addAttribute("isUsernameAlreadyTaken", true);
            return "index";
        }
        return "redirect:" + this.basePath;
    }
}

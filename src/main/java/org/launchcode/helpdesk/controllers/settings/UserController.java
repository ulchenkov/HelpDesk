package org.launchcode.helpdesk.controllers.settings;

import org.launchcode.helpdesk.data.UserRepository;
import org.launchcode.helpdesk.data.dto.UserDto;
import org.launchcode.helpdesk.helpers.SDHelper;
import org.launchcode.helpdesk.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("settings/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    private final String basePath = "/settings/users/";

    @GetMapping
    public String index(Model model) {

        SDHelper.initializeModel(model, this.basePath, "", "main-table");
        model.addAttribute("addLink", this.basePath + "add/");
        model.addAttribute("users", userRepository.findAll());

        return "index";
    }

    @GetMapping("add")
    public String displayAddForm(Model model) {

        SDHelper.initializeModel(model, this.basePath, "add", "add-user");
        model.addAttribute("userDto", new UserDto());

        return "index";
    }

    @PostMapping("add")
    public String processAddForm(
            @ModelAttribute @Valid UserDto userDto,
            Errors errors,
            Model model) {

        if (errors.hasErrors()) {
            SDHelper.initializeModel(model, this.basePath, "add", "add-user");
            return "index";
        }

        try {
            userRepository.save(new User(userDto));
        } catch (DataIntegrityViolationException exception) {
            SDHelper.initializeModel(model, this.basePath, "add", "add-user");
            model.addAttribute("isUsernameAlreadyTaken", true);
            return "index";
        }
        return "redirect:" + this.basePath;
    }



    @GetMapping("edit")
    public String displayEditForm(Model model, @RequestParam(required = false) Integer id) {

        if (id == null) {
            return "redirect:" + this.basePath;
        }

        SDHelper.initializeModel(model, this.basePath, "edit", "edit-user");
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return "index";
    }

    @PostMapping("edit")
    public String processEditForm(
            @ModelAttribute @Valid User updatedUser,
            Errors errors,
            @RequestParam int id,
            Model model) {

        if (errors.hasErrors()) {
            SDHelper.initializeModel(model, this.basePath, "edit", "edit-user");
            return "index";
        }

        try {
            User user = userRepository.findById(id).get();
            user.update(updatedUser);
            userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            SDHelper.initializeModel(model, this.basePath, "edit", "edit-user");
            model.addAttribute("isUsernameAlreadyTaken", true);
            return "index";
        }
        return "redirect:" + this.basePath;
    }

    @GetMapping("delete")
    public String displayDeleteForm(Model model, @RequestParam(required = false) Integer id) {

        if (id == null) {
            return "redirect:" + this.basePath;
        }

        SDHelper.initializeModel(model, this.basePath, "delete", "delete-user");
        User user = userRepository.findById(id).get();
        model.addAttribute("user", user);
        return "index";
    }

    @PostMapping("delete")
    public String processDeleteForm(
            @RequestParam int id,
            Model model) {

        try {
            userRepository.deleteById(id);
        } catch (DataIntegrityViolationException exception) {
            SDHelper.initializeModel(model, this.basePath, "delete", "fragments", "error");
            model.addAttribute("errorText", "User can not be deleted!");
            return "index";
        }
        return "redirect:" + this.basePath;
    }
}

package org.launchcode.helpdesk.controllers.auth;


import org.launchcode.helpdesk.controllers.AbstractBaseController;
import org.launchcode.helpdesk.data.GroupRepository;
import org.launchcode.helpdesk.data.UserRepository;
import org.launchcode.helpdesk.helpers.SDHelper;
import org.launchcode.helpdesk.models.Group;
import org.launchcode.helpdesk.models.User;
import org.launchcode.helpdesk.models.dto.UserDto;
import org.launchcode.helpdesk.models.enums.Role;
import org.launchcode.helpdesk.user.UserExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class AuthenticationController extends AbstractBaseController {

    private final String basePath = "/signin/";

    @Autowired
    UserRepository userRepository;
    @Autowired
    GroupRepository groupRepository;

//    @GetMapping("register")
//    public String displayRegisterForm(Model model) {
//        model.addAttribute("userDto", new UserDto());
//        return "register";
//    }
//
//    @PostMapping("register")
//    public String processRegisterForm(
//            @ModelAttribute @Valid UserDto userDto,
//            Errors errors,
//            Model model
//            ) {
//        if (errors.hasErrors()) {
//            return "register";
//        }
//
//        try {
//            userService.save(userDto);
//        } catch (UserExistException exception) {
//            errors.rejectValue("username", "username.alreadyexist", exception.getMessage());
//            return "register";
//        }
//
//        return "redirect:/";
//    }

    @GetMapping("signin")
    public String displayLogin(Model model, Principal user, String error, String logout) {

        if (userRepository.findAll().size() == 0) {
            Group adminGroup = null;
            for (Group group : groupRepository.findAll()) {
                if(group.getRoles().contains(Role.ADMIN)) {
                    adminGroup = group;
                    break;
                }
            }
            if (adminGroup == null) {
                Group group = new Group("HelpDesk Administrators");
                group.addRole(Role.ADMIN);
                groupRepository.save(group);
                adminGroup = group;
            }

            User adminUser = new User();
            adminUser.setUsername("admin");
            adminUser.setPassword("admin123");
            adminUser.setEmailAddress("admin@domen.com");
            adminUser.addGroup(adminGroup);
            adminUser.setActive(true);
            userRepository.save(adminUser);
        }

        SDHelper.initializeModel(model, this.basePath, "", "sign-in");

        if (user != null) {
            return "redirect:/";
        }

        if (error != null) {
            model.addAttribute(MESSAGE_KEY, "danger|Your username and password are invalid");
        }

        if (logout != null) {
            model.addAttribute(MESSAGE_KEY, "info|You have logged out");
        }

        return "index";
    }

}

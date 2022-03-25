package org.launchcode.helpdesk.controllers;

import org.launchcode.helpdesk.models.User;
import org.launchcode.helpdesk.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.security.Principal;

public class AbstractBaseController {

    @Autowired
    protected UserService userService;

    protected static final String MESSAGE_KEY = "message";

    @ModelAttribute("loggedInUser")
    public User getLoggedInUser(Principal principal) {
        if (principal != null) {
            return userService.findByUsername(principal.getName());
        }
        return null;
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return userService.findByUsername(currentPrincipalName);
    }
}

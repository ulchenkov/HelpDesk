package org.launchcode.helpdesk.controllers.settings;

import org.launchcode.helpdesk.controllers.AbstractBaseController;
import org.launchcode.helpdesk.data.GroupRepository;
import org.launchcode.helpdesk.helpers.SDHelper;
import org.launchcode.helpdesk.models.Group;
import org.launchcode.helpdesk.models.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("settings/groups")
public class GroupController extends AbstractBaseController {

    @Autowired
    GroupRepository groupRepository;

    private final String basePath = "/settings/groups/";

    @GetMapping
    public String index(Model model) {
        SDHelper.initializeModel(model, this.basePath, "", "main-table");
        model.addAttribute("addLink", this.basePath + "add/");
        model.addAttribute("groups", groupRepository.findAll());
        model.addAttribute("roles", Role.values());
        return "index";
    }

    @GetMapping("add")
    public String displayAddForm(Model model) {
        SDHelper.initializeModel(model, this.basePath, "add", "view-group");
        model.addAttribute("group", new Group());
        model.addAttribute("roles", Role.values());
        return "index";
    }

    @PostMapping("add")
    public String processAddForm(
            @ModelAttribute @Valid Group group,
            Errors errors,
            @RequestParam(required = false) Role[] roles,
            Model model) {

        if (errors.hasErrors()) {
            SDHelper.initializeModel(model, this.basePath, "add", "view-group");
            model.addAttribute("roles", Role.values());
            return "index";
        }

        if (roles != null) {
            for(Role role : roles) {
                group.addRole(role);
            }
        }

        groupRepository.save(group);
        return "redirect:" + this.basePath;
    }

    @GetMapping("edit")
    public String displayEditForm(Model model, @RequestParam(required = false) Integer id) {

        if (id == null) {
            return "redirect:" + this.basePath;
        }

        SDHelper.initializeModel(model, this.basePath, "edit", "view-group");
        model.addAttribute("group", groupRepository.findById(id).get());
        model.addAttribute("roles", Role.values());
        return "index";
    }

    @PostMapping("edit")
    public String processEditForm(
            @ModelAttribute @Valid Group editedGroup,
            Errors errors,
            @RequestParam int id,
            @RequestParam(required = false) Role[] roles,
            Model model) {

        if (errors.hasErrors()) {
            SDHelper.initializeModel(model, this.basePath, "edit", "view-group");
            model.addAttribute("roles", Role.values());
            return "index";
        }

        Group group = groupRepository.findById(id).get();
        group.setName(editedGroup.getName());
        group.clearRoles();
        if (roles != null) {
            for(Role role : roles) {
                group.addRole(role);
            }
        }
        groupRepository.save(group);
        return "redirect:" + this.basePath;
    }

    @GetMapping("delete")
    public String displayDeleteForm(Model model, @RequestParam(required = false) Integer id) {
        if (id == null) {
            return "redirect:" + this.basePath;
        }
        SDHelper.initializeModel(model, this.basePath, "delete", "delete-group");
        model.addAttribute("group", groupRepository.findById(id).get());
        return "index";
    }

    @PostMapping("delete")
    public String processDeleteForm(
            @RequestParam int id,
            Model model) {
        try {
            groupRepository.deleteById(id);}
        catch (DataIntegrityViolationException exception) {
            SDHelper.initializeModel(model, this.basePath, "delete", "fragments", "error");
            model.addAttribute("errorText", "Group can not be deleted!");
            return "index";
        }
        return "redirect:" + this.basePath;
    }
}

package org.launchcode.helpdesk.controllers.settings;

import org.launchcode.helpdesk.controllers.AbstractBaseController;
import org.launchcode.helpdesk.data.DepartmentRepository;
import org.launchcode.helpdesk.data.GroupRepository;
import org.launchcode.helpdesk.data.UserRepository;
import org.launchcode.helpdesk.models.Department;
import org.launchcode.helpdesk.models.Group;
import org.launchcode.helpdesk.models.dto.UserDto;
import org.launchcode.helpdesk.helpers.SDHelper;
import org.launchcode.helpdesk.models.User;
import org.launchcode.helpdesk.models.dto.UserGroupDTO;
import org.launchcode.helpdesk.user.UserExistException;
import org.launchcode.helpdesk.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("settings/users")
public class UserController extends AbstractBaseController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

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

        List<Department> departments = new ArrayList<>();
        departments.add(new Department("No department"));
        departments.addAll((List<Department>)departmentRepository.findAll());

        model.addAttribute("departments", departments);

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

        if (userDto.getDepartment() != null && userDto.getDepartment().getId() == 0) {
            userDto.setDepartment(null);
        }

        try {
            userService.save(userDto);
        } catch (UserExistException exception) {
            SDHelper.initializeModel(model, this.basePath, "add", "add-user");
            errors.rejectValue("username", "username.alreadyexist", exception.getMessage());
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
        model.addAttribute("departments", departmentRepository.findAll());
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
            userService.save(user);
        } catch (UserExistException exception) {
            SDHelper.initializeModel(model, this.basePath, "edit", "edit-user");
            errors.rejectValue("username", "username.alreadyexist", exception.getMessage());
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

    @GetMapping("edit-groups")
    public String displayGroupsForm(Model model, @RequestParam(required = false) Integer id) {
        if (id == null) {
            return "redirect:" + this.basePath;
        }

        Optional<User> opt = userRepository.findById(id);
        if (opt.isEmpty()) {
            SDHelper.initializeModel(model, this.basePath, "edit-groups", "fragments", "error");
            model.addAttribute("errorText", "Wrong user ID: " + id);
            return "index";
        }

        User user = opt.get();
        ArrayList<Group> groups = (ArrayList<Group>) groupRepository.findAll();
        for(Group group : user.getGroups()) {
            groups.remove(group);
        }

        UserGroupDTO userGroup = new UserGroupDTO();
        userGroup.setUser(user);

        SDHelper.initializeModel(model, this.basePath, "edit-groups", "user-edit-groups");
        model.addAttribute("user", user);
        model.addAttribute("groups", groups);
        model.addAttribute("userGroup", userGroup);
        return "index";
    }

    @PostMapping("edit-groups")
    public String processGroupForm(
            @ModelAttribute @Valid UserGroupDTO userGroup,
            Errors errors,
            Model model
    ) {
        if (errors.hasErrors()) {
            SDHelper.initializeModel(model, this.basePath, "edit-groups", "user-edit-groups");
            return "index";
        }

        User user = userGroup.getUser();
        Group group = userGroup.getGroup();

        if(!user.getGroups().contains(group)) {
            user.addGroup(group);
            userRepository.save(user);
        }
        return String.format("redirect:%s%s?id=%s", this.basePath, "edit-groups", user.getId());
    }

    @GetMapping("edit-groups-remove")
    public String processRemoveGroup(
            Model model,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) Integer groupId
    ) {
        if (userId == null || groupId == null) {
            return "redirect:" + this.basePath;
        }

        Optional<User> optUser = userRepository.findById(userId);
        Optional<Group> optGroup = groupRepository.findById(groupId);
        if (optUser.isEmpty() || optGroup.isEmpty()) {
            SDHelper.initializeModel(model, this.basePath, "edit-groups", "fragments", "error");
            model.addAttribute("errorText",
                    String.format("Wrong user ID [%s] or group ID [%s]", userId, groupId));
            return "index";
        }
        User user = optUser.get();
        Group group = optGroup.get();
        user.getGroups().remove(group);
        userRepository.save(user);
        return String.format("redirect:%s%s?id=%s", this.basePath, "edit-groups", user.getId());
    }
}

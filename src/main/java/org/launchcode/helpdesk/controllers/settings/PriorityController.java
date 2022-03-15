package org.launchcode.helpdesk.controllers.settings;

import org.launchcode.helpdesk.data.PriorityRepository;
import org.launchcode.helpdesk.helpers.SDHelper;
import org.launchcode.helpdesk.models.Priority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("settings/priorities")
public class PriorityController {

    @Autowired
    PriorityRepository priorityRepository;

    private final String basePath = "/settings/priorities/";

    @GetMapping
    public String index(Model model) {
        SDHelper.initializeModel(model, this.basePath, "", "main-table");
        model.addAttribute("addLink", this.basePath + "add/");
        model.addAttribute("priorities", priorityRepository.findAll());
        return "index";
    }

    @GetMapping("add")
    public String displayAddForm(Model model) {
        SDHelper.initializeModel(model, this.basePath, "add", "add-priority");
        model.addAttribute("priority", new Priority());
        return "index";
    }

    @PostMapping("add")
    public String processAddForm(
            @ModelAttribute @Valid Priority priority,
            Errors errors,
            Model model) {

        if (errors.hasErrors()) {
            SDHelper.initializeModel(model, this.basePath, "add", "add-priority");
            return "index";
        }

        priorityRepository.save(priority);
        return "redirect:" + this.basePath;
    }

    @GetMapping("edit")
    public String displayEditForm(Model model, @RequestParam(required = false) Integer id) {
        if (id == null) {
            return "redirect:" + this.basePath;
        }
        SDHelper.initializeModel(model, this.basePath, "edit", "edit-priority");
        model.addAttribute("priority", priorityRepository.findById(id).get());
        return "index";
    }

    @PostMapping("edit")
    public String processEditForm(
            @ModelAttribute @Valid Priority updatedPriority,
            Errors errors,
            @RequestParam int id,
            Model model) {

        if (errors.hasErrors()) {
            SDHelper.initializeModel(model, this.basePath, "edit", "edit-priority");
            return "index";
        }

        Priority priority = priorityRepository.findById(id).get();
        priority.setName(updatedPriority.getName());
        priorityRepository.save(priority);
        return "redirect:" + this.basePath;
    }

    @GetMapping("delete")
    public String displayDeleteForm(Model model, @RequestParam(required = false) Integer id) {
        if (id == null) {
            return "redirect:" + this.basePath;
        }
        SDHelper.initializeModel(model, this.basePath, "delete", "delete-priority");
        model.addAttribute("priority", priorityRepository.findById(id).get());
        return "index";
    }

    @PostMapping("delete")
    public String processDeleteForm(
            @RequestParam int id,
            Model model) {
        try {
            priorityRepository.deleteById(id);}
        catch (DataIntegrityViolationException exception) {
            SDHelper.initializeModel(model, this.basePath, "delete", "fragments", "error");
            model.addAttribute("errorText", "Priority can not be deleted!");
            return "index";
        }
        return "redirect:" + this.basePath;
    }
}

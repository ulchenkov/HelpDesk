package org.launchcode.helpdesk.controllers.settings;

import org.launchcode.helpdesk.controllers.AbstractBaseController;
import org.launchcode.helpdesk.data.DepartmentRepository;
import org.launchcode.helpdesk.helpers.SDHelper;
import org.launchcode.helpdesk.models.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("settings/departments")
public class DepartmentController extends AbstractBaseController {

    @Autowired
    DepartmentRepository departmentRepository;

    private final String basePath = "/settings/departments/";

    @GetMapping
    public String index(Model model) {
        SDHelper.initializeModel(model, this.basePath, "", "main-table");
        model.addAttribute("addLink", this.basePath + "add/");
        model.addAttribute("departments", departmentRepository.findAll());
        return "index";
    }

    @GetMapping("add")
    public String displayAddForm(Model model) {
        SDHelper.initializeModel(model, this.basePath, "add", "add-department");
        model.addAttribute("department", new Department());
        return "index";
    }

    @PostMapping("add")
    public String processAddForm(
            @ModelAttribute @Valid Department department,
            Errors errors,
            Model model) {

        if (errors.hasErrors()) {
            SDHelper.initializeModel(model, this.basePath, "add", "add-department");
            return "index";
        }

        departmentRepository.save(department);
        return "redirect:" + this.basePath;
    }

    @GetMapping("edit")
    public String displayEditForm(Model model, @RequestParam(required = false) Integer id) {

        if (id == null) {
            return "redirect:" + this.basePath;
        }

        SDHelper.initializeModel(model, this.basePath, "edit", "edit-department");
        model.addAttribute("department", departmentRepository.findById(id).get());
        return "index";
    }

    @PostMapping("edit")
    public String processEditForm(
            @ModelAttribute @Valid Department department,
            Errors errors,
            @RequestParam int id,
            Model model) {

        if (errors.hasErrors()) {
            SDHelper.initializeModel(model, this.basePath, "edit", "edit-department");
            return "index";
        }

        Department oldDepartment = departmentRepository.findById(id).get();
        oldDepartment.setName(department.getName());
        departmentRepository.save(oldDepartment);
        return "redirect:" + this.basePath;
    }

    @GetMapping("delete")
    public String displayDeleteForm(Model model, @RequestParam(required = false) Integer id) {

        if (id == null) {
            return "redirect:" + this.basePath;
        }

        SDHelper.initializeModel(model, this.basePath, "delete", "delete-department");
        model.addAttribute("department", departmentRepository.findById(id).get());
        return "index";
    }

    @PostMapping("delete")
    public String processDeleteForm(
            @RequestParam int id,
            Model model) {
        try {
            departmentRepository.deleteById(id);}
        catch (DataIntegrityViolationException exception) {
            SDHelper.initializeModel(model, this.basePath, "delete", "fragments", "error");
            model.addAttribute("errorText", "Department can not be deleted!");
            return "index";
        }
        return "redirect:" + this.basePath;
    }
}

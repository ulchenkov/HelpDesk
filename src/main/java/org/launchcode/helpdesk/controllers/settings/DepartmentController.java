package org.launchcode.helpdesk.controllers.settings;

import org.launchcode.helpdesk.data.DepartmentRepository;
import org.launchcode.helpdesk.data.helpers.LinkList;
import org.launchcode.helpdesk.data.helpers.Fragment;
import org.launchcode.helpdesk.data.helpers.SDHelper;
import org.launchcode.helpdesk.models.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("settings/departments")
public class DepartmentController {

    @Autowired
    DepartmentRepository departmentRepository;

    private final String fragmentFile = "settings/departments/fragments";
    private final String basePath = "/settings/departments/";

    @GetMapping
    public String index(Model model) {

        LinkList crumbs= SDHelper.getBreadCrumbs(this.basePath);
        model.addAttribute("breadCrumbs", crumbs.getAll());
        model.addAttribute("title",crumbs.getLast().getText());

        model.addAttribute(
                "content",
                new Fragment[] {new Fragment(this.fragmentFile, "main-table")});
        model.addAttribute("leftMenu", SettingsController.settingsMenu.getAll());
        model.addAttribute("leftMenuTitle", SettingsController.settingsMenuTitle);


        model.addAttribute("addLink", crumbs.getLast().getUrl() + "add/");

        model.addAttribute("departments", departmentRepository.findAll());
        return "index";
    }

    @GetMapping("add")
    public String displayAddForm(Model model) {
        LinkList crumbs= SDHelper.getBreadCrumbs(this.basePath + "add/");
        model.addAttribute("breadCrumbs", crumbs.getAll());
        model.addAttribute("title",crumbs.getLast().getText());

        model.addAttribute(
                "content",
                new Fragment[] {new Fragment(this.fragmentFile, "add-department")});
        model.addAttribute("leftMenu", SettingsController.settingsMenu.getAll());
        model.addAttribute("leftMenuTitle", SettingsController.settingsMenuTitle);

        model.addAttribute("department", new Department());
        return "index";
    }

    @PostMapping("add")
    public String processAddForm(
            @ModelAttribute @Valid Department department,
            Errors errors,
            Model model) {

        if (errors.hasErrors()) {
            LinkList crumbs= SDHelper.getBreadCrumbs(this.basePath + "add/");
            model.addAttribute("breadCrumbs", crumbs.getAll());
            model.addAttribute("title",crumbs.getLast().getText());

            model.addAttribute(
                    "content",
                    new Fragment[] {new Fragment(this.fragmentFile, "add-department")});
            model.addAttribute("leftMenu", SettingsController.settingsMenu.getAll());
            model.addAttribute("leftMenuTitle", SettingsController.settingsMenuTitle);

            return "settings/departments/add";
        }

        departmentRepository.save(department);
        return "redirect:" + this.basePath;
    }
}

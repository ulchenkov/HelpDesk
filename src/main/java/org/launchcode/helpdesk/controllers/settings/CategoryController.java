package org.launchcode.helpdesk.controllers.settings;

import org.launchcode.helpdesk.controllers.AbstractBaseController;
import org.launchcode.helpdesk.data.CategoryRepository;
import org.launchcode.helpdesk.helpers.SDHelper;
import org.launchcode.helpdesk.models.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("settings/categories")
public class CategoryController extends AbstractBaseController {

    @Autowired
    CategoryRepository categoryRepository;

    private final String basePath = "/settings/categories/";

    @GetMapping
    public String index(Model model) {
        SDHelper.initializeModel(model, this.basePath, "", "main-table");
        model.addAttribute("addLink", this.basePath + "add/");
        model.addAttribute("categories", categoryRepository.findAll());
        return "index";
    }

    @GetMapping("add")
    public String displayAddForm(Model model) {
        SDHelper.initializeModel(model, this.basePath, "add", "add-category");
        model.addAttribute("category", new Category());
        return "index";
    }

    @PostMapping("add")
    public String processAddForm(
            @ModelAttribute @Valid Category category,
            Errors errors,
            Model model) {

        if (errors.hasErrors()) {
            SDHelper.initializeModel(model, this.basePath, "add", "add-category");
            return "index";
        }

        categoryRepository.save(category);
        return "redirect:" + this.basePath;
    }

    @GetMapping("edit")
    public String displayEditForm(Model model, @RequestParam(required = false) Integer id) {

        if (id == null) {
            return "redirect:" + this.basePath;
        }

        SDHelper.initializeModel(model, this.basePath, "edit", "edit-category");
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "index";
    }

    @PostMapping("edit")
    public String processEditForm(
            @ModelAttribute @Valid Category updatedCategory,
            Errors errors,
            @RequestParam int id,
            Model model) {

        if (errors.hasErrors()) {
            SDHelper.initializeModel(model, this.basePath, "edit", "edit-category");
            return "index";
        }

        Category category = categoryRepository.findById(id).get();
        category.setName(updatedCategory.getName());
        category.setPrefix(updatedCategory.getPrefix());
        categoryRepository.save(category);
        return "redirect:" + this.basePath;
    }

    @GetMapping("delete")
    public String displayDeleteForm(Model model, @RequestParam(required = false) Integer id) {

        if (id == null) {
            return "redirect:" + this.basePath;
        }

        SDHelper.initializeModel(model, this.basePath, "delete", "delete-category");
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "index";
    }

    @PostMapping("delete")
    public String processDeleteForm(
            @RequestParam int id,
            Model model) {
        try {
            categoryRepository.deleteById(id);}
        catch (DataIntegrityViolationException exception) {
            SDHelper.initializeModel(model, this.basePath, "delete", "fragments", "error");
            model.addAttribute("errorText", "Category can not be deleted!");
            return "index";
        }
        return "redirect:" + this.basePath;
    }
}

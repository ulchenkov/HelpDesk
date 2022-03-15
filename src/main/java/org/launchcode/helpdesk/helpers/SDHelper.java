package org.launchcode.helpdesk.helpers;

import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.Map;

public class SDHelper {

    public static LinkList getBreadCrumbs(String path) {
        LinkList crumbs = new LinkList();
        Map<String, String> sections = new HashMap<>();
        sections.put("/", "Home");
        sections.put("/settings/", "Settings");
        sections.put("/settings/users/", "User list");
        sections.put("/settings/users/add/", "Add new user");
        sections.put("/settings/users/edit/", "Edit user");
        sections.put("/settings/users/delete/", "Delete user");
        sections.put("/settings/departments/", "Department list");
        sections.put("/settings/departments/add/", "Add new department");
        sections.put("/settings/departments/edit/", "Edit department details");
        sections.put("/settings/departments/delete/", "Delete department");
        sections.put("/settings/groups/", "Group list");
        sections.put("/settings/groups/add/", "Add new group");
        sections.put("/settings/groups/edit/", "Edit group details");
        sections.put("/settings/groups/delete/", "Delete group");
        sections.put("/settings/categories/", "Categories list");
        sections.put("/settings/categories/add/", "Add new category");
        sections.put("/settings/categories/edit/", "Edit category details");
        sections.put("/settings/categories/delete/", "Delete category");
        sections.put("/settings/priorities/", "Priorities list");
        sections.put("/settings/priorities/add/", "Add new priority");
        sections.put("/settings/priorities/edit/", "Edit priority details");
        sections.put("/settings/priorities/delete/", "Delete priority");
        int startIndex = 0;
        do {
            int index = path.indexOf("/", startIndex);
            if (index < 0) {
                break;
            }
            String node;
            if (index == 0) {
                node = "/";
            } else {
                node = path.substring(0, index + 1);
            }
            crumbs.add(new Link(sections.get(node), node));
            startIndex = index + 1;
        } while(true);
        return crumbs;
    }


    public static void initializeModel(Model model, String basePath, String action, Fragment[] mainContent) {

        String fullPath = action.isEmpty() ? basePath : basePath + action + "/";
        LinkList crumbs= SDHelper.getBreadCrumbs(fullPath);
        model.addAttribute("breadCrumbs", crumbs.getAll());
        model.addAttribute("title",crumbs.getLast().getText());

        LeftMenu menu = getLeftMenu(basePath);
        model.addAttribute("leftMenu", menu.getMenu().getAll());
        model.addAttribute("leftMenuTitle", menu.getTitle());

        model.addAttribute("content", mainContent);

    }

    public static void initializeModel(Model model, String basePath, String action, String mainContentFragmentFile, String mainContentFragmentName) {
        initializeModel(
                model,
                basePath,
                action,
                new Fragment[] {new Fragment(mainContentFragmentFile, mainContentFragmentName)});
    }

    public static void initializeModel(Model model, String basePath, String action, String mainContentFragmentName) {
        initializeModel(
                model,
                basePath,
                action,
                new Fragment[] {new Fragment(basePath + "fragments", mainContentFragmentName)});
    }

    private static LeftMenu getLeftMenu(String basePath) {
        if (basePath.split("/")[1].equals("settings")) {
            String path = "/settings/";
            LeftMenu menu = new LeftMenu("Settings");
            menu.getMenu().add(new Link("Users", path + "users/"));
            menu.getMenu().add(new Link("Groups", path + "groups/"));
            menu.getMenu().add(new Link("Departments", path + "departments/"));
            menu.getMenu().add(new Link("Ticket Categories", path + "categories/"));
            menu.getMenu().add(new Link("Ticket Priorities", path + "priorities/"));
            return menu;
        }
        else {
            throw new IllegalArgumentException("wrong path!");
        }
    }
}

package org.launchcode.helpdesk.data.helpers;

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
        sections.put("/settings/departments/", "Department list");
        sections.put("/settings/departments/add/", "Add new department");
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
}

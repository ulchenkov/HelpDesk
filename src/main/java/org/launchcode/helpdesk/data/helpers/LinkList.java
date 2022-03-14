package org.launchcode.helpdesk.data.helpers;

import java.util.ArrayList;
import java.util.List;

public class LinkList {
    private List<Link> links = new ArrayList<>();

    public List<Link> getAll() {
        return links;
    }

    public Link getLast() {
        return links.get(links.size() - 1);
    }

    public void add(Link link) {
        links.add(link);
    }
}

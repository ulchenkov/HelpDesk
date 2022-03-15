package org.launchcode.helpdesk.helpers;

public class LeftMenu {

    private final LinkList menu = new LinkList();
    private String title;

    public LeftMenu(String title) {
        this.title = title;
    }

    public LinkList getMenu() {
        return menu;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

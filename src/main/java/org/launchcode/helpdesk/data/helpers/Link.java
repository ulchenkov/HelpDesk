package org.launchcode.helpdesk.data.helpers;

public class Link {

    private String text;
    private String url;

    public Link(String text, String url) {
        this.text = text;
        this.url = url;
    }

    public String getText() {
        return text;
    }

    public String getUrl() {
        return url;
    }
}

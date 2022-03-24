package org.launchcode.helpdesk.models.enums;

public enum Role {
    ADMIN("ROLE_ADMIN", "HelpDesk Administrator"),
    USER("ROLE_USER", "HelpDesk User"),
    IT_SUPPORT("ROLE_ITSUPPORT", "IT Support Specialist");

    private final String springRole;
    private final String text;

    Role(String springRole, String text) {
        this.springRole = springRole;
        this.text = text;
    }

    public String getSpringRole() {
        return springRole;
    }

    public String getText() {
        return text;
    }
}

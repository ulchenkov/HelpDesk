package org.launchcode.helpdesk.models.enums;

public enum Status {
    CREATED("Created"),
    ASSIGNED("Assigned"),
    PENDING("Pending"),
    SOLVED("Solved"),
    CLOSED("Closed");

    private final String text;

    Status(String text) {
        this.text = text;
    }

    public String getText() {
        return this.text;
    }
}

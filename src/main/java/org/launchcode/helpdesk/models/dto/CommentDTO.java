package org.launchcode.helpdesk.models.dto;

import org.launchcode.helpdesk.models.Ticket;
import org.launchcode.helpdesk.models.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CommentDTO {

    @NotNull
    private User author;

    @NotNull
    @NotBlank(message = "Comments must be at least 1 character long")
    private String text;

    @NotNull
    private Ticket ticket;

    public CommentDTO() {
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}

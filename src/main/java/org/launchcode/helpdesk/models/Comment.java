package org.launchcode.helpdesk.models;

import org.launchcode.helpdesk.models.dto.CommentDTO;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@Entity
public class Comment extends AbstractEntity {

    @NotNull
    @ManyToOne
    private User author;

    @NotNull
    private final LocalDateTime date;

    @NotNull
    private String text;

    @ManyToOne
    private Ticket ticket;

    private boolean isSystem;

    public Comment() {
        this.date = LocalDateTime.now();
        this.isSystem = false;
    }

    public Comment(CommentDTO newComment) {
        this();
        this.author = newComment.getAuthor();
        this.ticket = newComment.getTicket();
        this.text = newComment.getText();
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDateTime getDate() {
        return date;
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

    public boolean isSystem() {
        return isSystem;
    }

    public void setSystem(boolean system) {
        isSystem = system;
    }

    public String getDateFormatted() {
        return this.date.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }
}

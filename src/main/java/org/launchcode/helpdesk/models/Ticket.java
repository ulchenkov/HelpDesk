package org.launchcode.helpdesk.models;

import org.launchcode.helpdesk.models.enums.Status;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Ticket extends AbstractEntity {

    @NotNull(message = "Requester is required")
    @ManyToOne
    private User requester;

    @NotNull(message = "Creator is required")
    @ManyToOne
    private User createdBy;

    private LocalDateTime created;

    @NotNull(message = "Title is required")
    @Size(min = 5, message = "Title must be at least 5 characters long")
    private String title;
    private String description;

    @NotNull(message = "Category is required")
    @ManyToOne
    private Category category;

    @NotNull(message = "Priority is required")
    @ManyToOne
    private Priority priority;

    private LocalDateTime dueDate;

    private Status status;

    @OneToMany(mappedBy = "ticket")
    private final List<Comment> comments = new ArrayList<>();

    public Ticket() {
        this.created = LocalDateTime.now();
        this.status = Status.CREATED;
    }

    public void update(Ticket ticket) {
        this.requester = ticket.requester;
        this.category = ticket.category;
        if (this.priority != ticket.priority) {
            setPriority(ticket.priority);
        };
        this.title = ticket.title;
        this.description = ticket.description;
        this.status = ticket.status;

    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(User createdBy) {
        this.createdBy = createdBy;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
        this.dueDate = this.created.plus(priority.getTimeframe(), ChronoUnit.HOURS);
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public String getDueDateFormatted() {
        if (this.dueDate == null) {
            return "";
        }
        return this.dueDate.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM));
    }

    public String getCreatedFormatted() {
        if (this.created == null) {
            return "";
        }
        return this.created.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
    }

    public String getFullId() {
        if (this.category == null) {
            return ((Integer)this.getId()).toString();
        }
        return String.format("%s-%s", this.category.getPrefix(), this.getId());
    }

    public List<Comment> getComments() {
        return comments;
    }
}

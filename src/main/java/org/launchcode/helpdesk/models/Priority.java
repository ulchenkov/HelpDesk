package org.launchcode.helpdesk.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Priority extends AbstractEntity {

    @NotBlank(message = "Priority name is required")
    @Size(min = 3, message = "Priority name must be at least 3 characters long")
    private String name;

    @OneToMany(mappedBy = "priority")
    private final List<Ticket> tickets = new ArrayList<>();

    public Priority() {
    }

    public Priority(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
}

package org.launchcode.helpdesk.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends AbstractEntity {

    @NotBlank(message = "Category name is required")
    @Size(min = 3, message = "Category name must be at least 3 characters long")
    private String name;

    @OneToMany(mappedBy = "category")
    private final List<Ticket> tickets = new ArrayList<>();

    public Category() {
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Ticket> getRequests() {
        return tickets;
    }
}

package org.launchcode.helpdesk.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Category extends AbstractEntity {

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

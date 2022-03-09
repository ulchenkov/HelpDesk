package org.launchcode.helpdesk.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Department extends AbstractEntity{
    private String name;

    @OneToMany(mappedBy = "department")
    private final List<User> users = new ArrayList<>();

    public Department() {
    }

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }
}

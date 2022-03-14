package org.launchcode.helpdesk.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Department extends AbstractEntity{

    @NotBlank(message = "Department name is required")
    @Size(min = 3, message = "Department name must be at least 3 characters long")
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

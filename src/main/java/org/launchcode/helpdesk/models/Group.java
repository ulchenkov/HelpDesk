package org.launchcode.helpdesk.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "group_")
public class Group extends AbstractEntity{

    @NotBlank(message = "Group name is required")
    @Size(min = 3, message = "Group name must be at least 3 characters long")
    private String name;

    @ManyToMany(mappedBy = "groups")
    private final List<User> users = new ArrayList<>();

    public Group() {
    }

    public Group(String name) {
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

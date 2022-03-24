package org.launchcode.helpdesk.models;

import org.launchcode.helpdesk.models.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "group_")
public class Group extends AbstractEntity{

    @NotBlank(message = "Group name is required")
    @Size(min = 3, message = "Group name must be at least 3 characters long")
    private String name;

    @ManyToMany(mappedBy = "groups")
    private final List<User> users = new ArrayList<>();

    private String roles;


    public Group() {
        this.roles = "";
    }

    public Group(String name) {
        this();
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

    public List<Role> getRoles() {
        ArrayList<Role> roles = new ArrayList<>();

        if (!this.roles.isEmpty()) {
            for(String roleName : this.roles.split(":")) {
                roles.add(Role.valueOf(roleName));
            }
        }

        return roles;
    }

    public void addRole(Role role) {

        List<String> roleNames = Arrays.asList(this.roles.split(":"));

        if (role == null || roleNames.contains(role.name())) {
            return;
        }
        if (this.roles.isEmpty()) {
            this.roles = role.name();
        } else {
            this.roles += ":" + role.name();
        }
    }

    public void clearRoles() {
        this.roles = "";
    }

    public boolean hasRole(String roleName) {
        return Arrays.asList(this.roles.split(":")).contains(roleName);
    }
}

package org.launchcode.helpdesk.models;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity{

    private String firstName;
    private String lastName;

    @ManyToOne
    private Department department;

    @ManyToMany
    private final List<Group> groups = new ArrayList<>();

    @OneToMany(mappedBy = "requester")
    private final List<Ticket> requestedTickets = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy")
    private final List<Ticket> createdTickets = new ArrayList<>();

    public User() { }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public List<Ticket> getRequestedTickets() {
        return requestedTickets;
    }

    public List<Ticket> getCreatedTickets() {
        return createdTickets;
    }
}

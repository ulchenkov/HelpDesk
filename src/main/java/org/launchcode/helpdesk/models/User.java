package org.launchcode.helpdesk.models;

import org.hibernate.validator.constraints.UniqueElements;
import org.launchcode.helpdesk.data.dto.UserDto;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User extends AbstractEntity{

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    private String firstName;
    private String lastName;

    @Email(message = "Wrong email address")
    @NotBlank(message = "Email address is required")
    private String emailAddress;
    private String phoneNumber;

    @NotBlank(message = "Username is required")
    @Size(min = 3, message = "User name must be at least 3 characters long")
    @Column(unique = true)
    private String username;
    String passwordHash;

    private boolean isActive;

    @ManyToOne
    private Department department;

    @ManyToMany
    private final List<Group> groups = new ArrayList<>();

    @OneToMany(mappedBy = "requester")
    private final List<Ticket> requestedTickets = new ArrayList<>();

    @OneToMany(mappedBy = "createdBy")
    private final List<Ticket> createdTickets = new ArrayList<>();

    public User() { }

    public User(UserDto newUser) {
        this.firstName = newUser.getFirstName();
        this.lastName = newUser.getLastName();
        this.department = newUser.getDepartment();
        this.emailAddress = newUser.getEmailAddress();
        this.phoneNumber = newUser.getPhoneNumber();
        this.isActive = newUser.isActive();

        this.username = newUser.getUsername();
        this.passwordHash = encoder.encode(newUser.getPassword());
    }

    public void update(User updatedUser) {
        this.firstName = updatedUser.getFirstName();
        this.lastName = updatedUser.getLastName();
        this.department = updatedUser.getDepartment();
        this.emailAddress = updatedUser.getEmailAddress();
        this.phoneNumber = updatedUser.getPhoneNumber();
        this.isActive = updatedUser.isActive();
        this.username = updatedUser.getUsername();
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

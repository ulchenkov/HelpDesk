package org.launchcode.helpdesk.models.dto;

import org.launchcode.helpdesk.models.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserDto extends User {

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;

    @NotBlank(message = "Passwords do not match")
    private String verifyPassword;

    public UserDto() {
        this.setActive(true);
    }

//    public UserDto(User user) {
//        this.setFirstName(user.getFirstName());
//        this.setLastName(user.getLastName());
//        this.setDepartment(user.getDepartment());
//        this.setEmailAddress(user.getEmailAddress());
//        this.setPhoneNumber(user.getPhoneNumber());
//        this.setActive(user.isActive());
//        this.setUsername(user.getUsername());
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
        if (!this.password.equals(this.verifyPassword)) {
            setVerifyPassword("");
        }
    }

    public String getVerifyPassword() {
        return verifyPassword;
    }

    public void setVerifyPassword(String verifyPassword) {
        if (!verifyPassword.equals(this.password)) {
            this.verifyPassword = "";
        } else {
            this.verifyPassword = verifyPassword;
        }
    }

}

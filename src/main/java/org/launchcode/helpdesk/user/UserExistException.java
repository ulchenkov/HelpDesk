package org.launchcode.helpdesk.user;

public class UserExistException extends Exception {

    public UserExistException(String message) {
        super(message);
    }
}

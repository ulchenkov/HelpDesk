package org.launchcode.helpdesk.models.dto;

import org.launchcode.helpdesk.models.Group;
import org.launchcode.helpdesk.models.User;

import javax.validation.constraints.NotNull;

public class UserGroupDTO {
    @NotNull
    private User user;

    @NotNull
    private Group group;

    public UserGroupDTO() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }
}

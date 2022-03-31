package org.launchcode.helpdesk.user;

import org.launchcode.helpdesk.models.User;
import org.launchcode.helpdesk.models.dto.UserDto;

public interface UserService {

    public User save(User user) throws UserExistException;
    public User save(UserDto userDto) throws UserExistException;
    public User findByUsername(String username);

}

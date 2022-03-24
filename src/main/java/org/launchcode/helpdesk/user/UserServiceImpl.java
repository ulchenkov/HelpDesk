package org.launchcode.helpdesk.user;

import org.launchcode.helpdesk.data.UserRepository;
import org.launchcode.helpdesk.models.User;
import org.launchcode.helpdesk.models.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public User save(UserDto userDto) throws UserExistException {

        User existingUser = findByUsername(userDto.getUsername());
        if (existingUser != null) {
            throw new UserExistException(
                    String.format("The username %s already exists in the system", userDto.getUsername()));
        }

        User newUser = new User(userDto);
        userRepository.save(newUser);

        return newUser;
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}

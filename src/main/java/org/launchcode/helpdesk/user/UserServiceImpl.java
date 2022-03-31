package org.launchcode.helpdesk.user;

import org.launchcode.helpdesk.data.UserRepository;
import org.launchcode.helpdesk.models.User;
import org.launchcode.helpdesk.models.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User save(User user) throws UserExistException {

//        User exUser = userRepository.findByUsername(user.getUsername());
//        boolean userExist = exUser != null;
//        if (userExist) {
//            boolean userTheSame = exUser.getId() == user.getId();
//            if (!userTheSame) {
//                return null;
//            }
//        }


        if (isUsernameAlreadyExist(user)) {
            throw new UserExistException(
                    String.format("The username %s already exists in the system", user.getUsername()));
        }
        userRepository.save(user);
        return user;
    }

    @Transactional
    @Override
    public User save(UserDto userDto) throws UserExistException {
        User newUser = new User(userDto);
        return save(newUser);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    private boolean isUsernameAlreadyExist (User user) {
        User existingUser = findByUsername(user.getUsername());
        return existingUser != null && existingUser.getId() != user.getId();
    }
}

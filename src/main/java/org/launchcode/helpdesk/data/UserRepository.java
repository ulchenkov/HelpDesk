package org.launchcode.helpdesk.data;

import org.launchcode.helpdesk.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}

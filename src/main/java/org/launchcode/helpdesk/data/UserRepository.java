package org.launchcode.helpdesk.data;

import org.launchcode.helpdesk.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends CrudRepository<User, Integer> {
}

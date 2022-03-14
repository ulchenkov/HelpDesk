package org.launchcode.helpdesk.data;

import org.launchcode.helpdesk.models.Group;
import org.springframework.data.repository.CrudRepository;

public interface GroupRepository extends CrudRepository<Group, Integer> {
}

package org.launchcode.helpdesk.data;

import org.launchcode.helpdesk.models.Priority;
import org.springframework.data.repository.CrudRepository;

public interface PriorityRepository extends CrudRepository<Priority, Integer> {
}

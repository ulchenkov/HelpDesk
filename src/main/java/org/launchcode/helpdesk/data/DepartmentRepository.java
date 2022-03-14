package org.launchcode.helpdesk.data;

import org.launchcode.helpdesk.models.Department;
import org.springframework.data.repository.CrudRepository;

public interface DepartmentRepository extends CrudRepository<Department, Integer> {
}

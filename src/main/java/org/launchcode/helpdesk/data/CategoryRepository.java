package org.launchcode.helpdesk.data;

import org.launchcode.helpdesk.models.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
}

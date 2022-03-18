package org.launchcode.helpdesk.data;

import org.launchcode.helpdesk.models.Comment;
import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment, Integer> {
}

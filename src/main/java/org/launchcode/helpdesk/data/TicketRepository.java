package org.launchcode.helpdesk.data;

import org.launchcode.helpdesk.models.Ticket;
import org.launchcode.helpdesk.models.User;
import org.launchcode.helpdesk.models.enums.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface TicketRepository extends CrudRepository<Ticket, Integer> {

    List<Ticket> findByRequesterAndStatusIn(User requester, Collection<Status> statuses);
    List<Ticket> findByCreatedByAndStatusIn(User createdBy, Collection<Status> statuses);
    List<Ticket> findByStatusIn(Collection<Status> statuses);
}

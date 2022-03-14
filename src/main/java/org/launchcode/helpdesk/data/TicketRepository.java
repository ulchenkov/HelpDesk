package org.launchcode.helpdesk.data;

import org.launchcode.helpdesk.models.Ticket;
import org.springframework.data.repository.CrudRepository;

public interface TicketRepository extends CrudRepository<Ticket, Integer> {
}

package org.launchcode.helpdesk.helpers;

import org.launchcode.helpdesk.models.Ticket;

import java.util.HashSet;
import java.util.Set;

public class TicketBlock {

    private String title;
    private final Set<Ticket> tickets = new HashSet<>();

    public TicketBlock(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }
}

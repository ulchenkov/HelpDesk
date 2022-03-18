package org.launchcode.helpdesk.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Priority extends AbstractEntity {

    @NotBlank(message = "Priority name is required")
    @Size(min = 3, message = "Priority name must be at least 3 characters long")
    private String name;

    @NotNull(message = "Timeframe is required")
    @Min(value = 1, message = "Timeframe cannot be less than 1 hour")
    private Integer timeframe; // timeframe for resolving, hours.

    @OneToMany(mappedBy = "priority")
    private final List<Ticket> tickets = new ArrayList<>();

    public Priority() {
        this.timeframe = 1;
    }

    public Priority(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTimeframe() {
        return timeframe;
    }

    public void setTimeframe(Integer timeframe) {
        this.timeframe = timeframe;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
}

package com.example.Prototype.client;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@PrimaryKeyJoinColumn(name="cinematcketID")
public class CinemaTicket extends Ticket implements Serializable {

    @ManyToOne
    @JoinColumn(name = "multiple_ticket_id")
    private MultipleTickets multiple_tickets = null;

    public CinemaTicket(){
        super(100);
    }

    public CinemaTicket(Time details){
        super(details,100);
    }

    public MultipleTickets getMultiple_tickets() {
        return multiple_tickets;
    }

    public void setMultiple_tickets(MultipleTickets multiple_tickets) {
        this.multiple_tickets = multiple_tickets;
    }
}

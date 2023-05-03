package com.example.Prototype.client;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "multiple_tickets")
public class MultipleTickets implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "multiple_tickets")
    private List<CinemaTicket> ticketList;

    public static double price=1200.0;

    public MultipleTickets(){
        this.ticketList=new ArrayList<CinemaTicket>();
    }

    public List<CinemaTicket> getTicketList() {
        if(ticketList.size() < 20)
            return null;
        return ticketList;
    }

    public void setTicketList(List<CinemaTicket> ticketList) {
        this.ticketList = ticketList;
    }

    public void addTicket(CinemaTicket ticket){
        this.ticketList.add(ticket);
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

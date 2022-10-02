package com.example.Prototype.client;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="seats")
public class Seat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int status;

    private String name;

    @ManyToOne
    @JoinColumn(name = "map_id")
    private Map map;

    @OneToOne(
            cascade = {CascadeType.ALL}
    )
    @JoinColumn(
            name = "seat_ticket",
            referencedColumnName = "id"
    )
    private Ticket ticket;

    public Seat() {
        this.status =0;
    }

   /* public Seat(int status, String name) {
        this.id = id;
        this.status = status;
        this.name = name;
        this.ticket=new CinemaTicket(map.getTime());
        map.getTime().addTicket(this.ticket);
        this.ticket.setSeat(map.getSeats().get(id));
    }*/

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(CinemaTicket ticket) {
        this.ticket = ticket;
    }
}

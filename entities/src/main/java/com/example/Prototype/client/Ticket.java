package com.example.Prototype.client;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tickets")
@Inheritance(strategy=InheritanceType.JOINED)
public abstract class Ticket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "details_id")
    private Time details;

    @NotNull
    @Column(name="ticket_price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "costumer_id")
    private Costumer costumer;

    @ManyToOne
    @JoinColumn(name = "purchase_account_id")
    private Account purchase_account;

    @OneToOne(
            fetch = FetchType.LAZY,
            mappedBy = "ticket"
    )
    private Seat seat;

    public Ticket(){}

    public Ticket(double price){
        this.costumer=null;
        this.price=price;
    }

    public Ticket(Time details,double price){
        this.costumer=null;
        this.details=details;
        this.price=price;
    }

    public int getId() {
        return id;
    }

    public Time getDetails() {
        return details;
    }

    public void setDetails(Time details) {
        this.details = details;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Costumer getCostumer() {
        return costumer;
    }

    public void setCostumer(Costumer costumer) {
        this.costumer = costumer;
        this.details.setAvailableSeats(this.details.getAvailableSeats() - 1);
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public Account getPurchase_account() {
        return purchase_account;
    }

    public void setPurchase_account(Account purchase_account) {
        this.purchase_account = purchase_account;
    }
}

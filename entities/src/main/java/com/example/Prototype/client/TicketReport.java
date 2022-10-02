package com.example.Prototype.client;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name="ticketreportID")
public class TicketReport extends Report implements Serializable {

    @NotNull
    private double price;

    @NotNull
    private String branch;

    @NotNull
    private String hall;

    @NotNull
    private String seat;

    public TicketReport() {
    }

    public TicketReport(String costumer_name, String movie, Date date, String branch, String hall, String seat, double price) {
        super(costumer_name, movie, date);
        this.branch = branch;
        this.hall = hall;
        this.seat = seat;
        this.price = price;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public String getHall() {
        return hall;
    }

    public void setHall(String hall) {
        this.hall = hall;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

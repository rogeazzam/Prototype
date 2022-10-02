package com.example.Prototype.client;

import com.sun.istack.NotNull;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@PrimaryKeyJoinColumn(name="hometicketID")
public class HomeTicket extends Ticket implements Serializable {

    @NotNull
    @Column(name = "URL")
    private String URL;

    public HomeTicket(){
        super(70);
    }

    public HomeTicket(Time details){
        super(details,70);
    }
}

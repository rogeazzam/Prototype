package com.example.Prototype.client;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "map")
public class Map implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String seats;

    @Column(name = "NumOfSeats")
    private int size;

    @OneToOne(mappedBy = "map")
    private Hall hall;

    Map(){
        this.size=80;
        seats="";
        for(int i=0; i < 80; i++)
            seats+="0";
    }

    public String getSeats(){
        return this.seats;
    }

    public void setSeats(String seats){
        this.seats=seats;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall){
        this.hall=hall;
    }
}

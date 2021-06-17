/*package com.example.Prototype.client;

import javax.persistence.*;
import java.io.IOException;

@Entity
@Table(name = "maps")
public class Map {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @OneToOne(
            mappedBy = "map"
    )
    private Hall hall;

    //int seatChosen[];

    int seats[];

    public Map(){
        seats=new int[80];
    }

    public Hall getHall() {
        return hall;
    }

    public void setHall(Hall hall) {
        this.hall = hall;
    }
}*/

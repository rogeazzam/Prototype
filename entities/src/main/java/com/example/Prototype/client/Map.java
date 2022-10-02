package com.example.Prototype.client;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "map")
public class Map implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "map", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Seat> seats=new ArrayList<Seat>();

    @Column(name = "NumOfSeats")
    private int size;

    @OneToOne(
            fetch = FetchType.LAZY,
            mappedBy = "map"
    )
    private Time time;

    Map(){
        this.size=80;
        for (int i = 0; i < 80; i++) {
            char c=(char)((int)('a') + (int) i/8);
            String name=c + String.valueOf((i%8) + 1);
            Seat seat = new Seat();
            seat.setMap(this);
            seat.setName(name);
            CinemaTicket ticket=new CinemaTicket(time);
            seat.setTicket(ticket);
            ticket.setSeat(seat);
            seats.add(seat);
        }
    }

    public List<Seat> getSeats(){
        return this.seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Time getTime(){
        if(time!=null)
            return this.time;
        return null;
    }

    public void setTime(Time time){
        this.time=time;
        for(Seat seat:seats) {
            time.addTicket(seat.getTicket());
            seat.getTicket().setDetails(time);
        }
    }
}

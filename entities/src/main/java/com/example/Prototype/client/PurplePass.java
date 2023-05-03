package com.example.Prototype.client;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "purple_pass")
public class PurplePass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private static PurplePass purplePass;

    @Column(name="status")
    private boolean status;  //false if there's no limitations, true otherwise.

    @Column(name = "max_seats")
    private int maxSeats;

    private Timestamp beg;

    private Timestamp end;

    protected PurplePass(){
        status=false;
        maxSeats=-1;
    }

    public static PurplePass PurplePass(){
        if(purplePass==null)
            purplePass=new PurplePass();
        return purplePass;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(int maxSeats) {
        this.maxSeats = maxSeats;
    }

    public Timestamp getBeg() {
        return beg;
    }

    public void setBeg(Timestamp beg) {
        this.beg = beg;
    }

    public Timestamp getEnd() {
        return end;
    }

    public void setEnd(Timestamp end) {
        this.end = end;
    }
}

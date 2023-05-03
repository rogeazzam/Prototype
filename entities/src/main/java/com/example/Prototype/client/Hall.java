package com.example.Prototype.client;

import com.sun.istack.NotNull;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="halls")
public class Hall implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(unique = true)
    private String name;

    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch=null;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY, mappedBy = "hall", orphanRemoval = true)
    @Fetch(FetchMode.SUBSELECT)
    private List<Time> screeningTime;

    private int seatsNum=80;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Hall(){
        this.screeningTime=new ArrayList<Time>();
    }

    public Branch getBranch(){
        return this.branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public List<Time> getScreeningTime(){
        return this.screeningTime;
    }

    public void setScreeningTime(List<Time> screeningTime) {
        this.screeningTime = screeningTime;
    }

    public void addScreeningTime(Time time){
        this.screeningTime.add(time);
    }

    public int getSeatsNum() {
        return seatsNum;
    }
}

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
    private Branch branch;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "map_id", referencedColumnName = "id")
    private Map map;

    @OneToMany(cascade = CascadeType.MERGE,fetch = FetchType.LAZY, mappedBy = "hall")
    @Fetch(FetchMode.SUBSELECT)
    private List<Time> screeningTime;

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

    public Map getMap(){
        return this.map;
    }

    public void setMap(Map map){
        this.map=map;
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
}

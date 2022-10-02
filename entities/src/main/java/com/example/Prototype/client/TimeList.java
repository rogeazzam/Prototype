package com.example.Prototype.client;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TimeList implements Serializable {

    private List<Time> toUpdate=new ArrayList<Time>();

    public TimeList() {
    }

    public TimeList(List<Time> toUpdate) {
        this.toUpdate = toUpdate;
    }

    public List<Time> getToUpdate() {
        return toUpdate;
    }

    public void setToUpdate(List<Time> toUpdate) {
        this.toUpdate = toUpdate;
    }

    public void addToUpdate(Time time){
        this.toUpdate.add(time);
    }
}

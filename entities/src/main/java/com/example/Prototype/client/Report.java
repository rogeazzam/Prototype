package com.example.Prototype.client;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "reports")
@Inheritance(strategy=InheritanceType.JOINED)
public class Report implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String costumer_name;

    private String movie;

    @NotNull
    private Date date;

    public Report() {
    }

    public Report(String costumer_name, String movie, Date date) {
        this.costumer_name = costumer_name;
        this.movie = movie;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public String getCostumer_name() {
        return costumer_name;
    }

    public void setCostumer_name(String costumer_name) {
        this.costumer_name = costumer_name;
    }

    public String getMovie() {
        return movie;
    }

    public void setMovie(String movie) {
        this.movie = movie;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}

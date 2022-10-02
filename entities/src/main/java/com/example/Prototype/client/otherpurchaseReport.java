package com.example.Prototype.client;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name="otherreportID")
public class otherpurchaseReport extends Report implements Serializable {

    @NotNull
    private double price;

    public otherpurchaseReport() {
    }

    public otherpurchaseReport(String costumer_name, String movie, Date date, double price) {
        super(costumer_name, movie, date);
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

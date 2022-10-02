package com.example.Prototype.client;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name="refundreportID")
public class RefundReport extends Report implements Serializable {

    @NotNull
    private double returnValue;

    private String branch_name=null;

    public RefundReport() {
    }

    public RefundReport(String costumer_name, String movie, Date date, double returnValue) {
        super(costumer_name, movie, date);
        this.returnValue = returnValue;
    }

    public double getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(double returnValue) {
        this.returnValue = returnValue;
    }

    public String getBranch_name() {
        return branch_name;
    }

    public void setBranch_name(String branch_name) {
        this.branch_name = branch_name;
    }
}

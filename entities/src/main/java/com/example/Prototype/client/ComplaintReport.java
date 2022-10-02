package com.example.Prototype.client;

import com.sun.istack.NotNull;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name="complaintreportID")
public class ComplaintReport extends Report implements Serializable {
    @NotNull
    @ManyToOne
    @JoinColumn(name = "costumer_id")
    private Costumer costumer;

    private String subject;

    @NotNull
    private String text;

    @NotNull
    private boolean answered=false;

    private String creditCardNum;

    private String validityMonth;

    private String validityYear;

    private String cvvNum;

    public ComplaintReport() {
    }

    public ComplaintReport(String costumer_name, Date date, Costumer costumer, String subject, String text) {
        super(costumer_name, "", date);
        this.costumer = costumer;
        this.subject = subject;
        this.text = text;
    }

    public Costumer getCostumer() {
        return costumer;
    }

    public void setCostumer(Costumer costumer) {
        this.costumer = costumer;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public String getCreditCardNum() {
        return creditCardNum;
    }

    public void setCreditCardNum(String creditCardNum) {
        this.creditCardNum = creditCardNum;
    }

    public String getValidityMonth() {
        return validityMonth;
    }

    public void setValidityMonth(String validityMonth) {
        this.validityMonth = validityMonth;
    }

    public String getValidityYear() {
        return validityYear;
    }

    public void setValidityYear(String validityYear) {
        this.validityYear = validityYear;
    }

    public String getCvvNum() {
        return cvvNum;
    }

    public void setCvvNum(String cvvNum) {
        this.cvvNum = cvvNum;
    }
}

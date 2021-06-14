package com.example.Prototype.client;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.*;

@Entity
@Table(name = "person")
public class Employee extends Person{
    //private List<Report> reports;
    private String ComplaintSolve;

    public Employee(String firstname, String lastname, String Mail, String Password, String UserName){
        super(firstname,lastname,Mail,Password,UserName);
        this.type="Emplyee";
    }

    /*public List<Report> getReports() {
        return reports;
    }*/

    public String getComplaintSolve() {
        return ComplaintSolve;
    }

    /*public void setReports(List<Report> reports) {
        this.reports = reports;
    }*/

    public void setComplaintSolve(String complaintSolve) {
        ComplaintSolve = complaintSolve;
    }
}

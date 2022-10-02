package com.example.Prototype.client;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="person")
public class NetworkManager extends Person{
    //private List<Report> reports;

    public NetworkManager(){
        super();
        this.type="NetworkManager";
    }

    public NetworkManager(String firstname, String lastname, String Mail, String Password, String UserName){
        super(firstname,lastname,Mail,Password,UserName);
        this.type="NetworkManager";
    }

    /*public List<Report> getReports() {
        return reports;
    }*/

    @Override
    public String getType(){
        return this.type;
    }

    /*public void setReports(List<Report> reports) {
        this.reports = reports;
     }*/

    @Override
    public void setType(String type) {
        super.setType(type);
    }
}

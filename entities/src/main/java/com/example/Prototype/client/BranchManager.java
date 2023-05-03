package com.example.Prototype.client;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="person")
public class BranchManager extends Person {

    public BranchManager(){
        super();
        this.type = "BranchManager";
    }

    public BranchManager(String firstname, String lastname, String Mail, String Password, String UserName) {
        super(firstname, lastname, Mail, Password, UserName);
        this.type = "BranchManager";
    }

    @Override
    public String getType() {
        return this.type;
    }

    @Override
    public void setType(String type) {
        super.setType(type);
    }
}
package com.example.Prototype.client;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "person")
public class Employee extends Person{

    public Employee(){
        super();
        this.type="Employee";
    }

    public Employee(String firstname, String lastname, String Mail, String Password, String UserName){
        super(firstname,lastname,Mail,Password,UserName);
        this.type="Employee";
    }

    @Override
    public String getType(){
        return this.type;
    }

    @Override
    public void setType(String type) {
        super.setType(type);
    }
}

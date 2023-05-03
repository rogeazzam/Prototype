package com.example.Prototype.client;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="person")
public class ContentManager extends Person{

    public ContentManager(){
        super();
        this.type="ContentManager";
    }

    public ContentManager(String firstname, String lastname, String Mail, String Password, String UserName){
        super(firstname,lastname,Mail,Password,UserName);
        this.type="ContentManager";
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

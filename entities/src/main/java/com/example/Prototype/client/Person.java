package com.example.Prototype.client;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "person")
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Column(name="first_name")
    private String firstname;

    @NotNull
    @Column(name="last_name")
    private String lastname;

    @NotNull
    @Column(name="mail")
    private String Mail;

    @NotNull
    @Column(name="password")
    private String Password;

    @NotNull
    @Column(name="user_name")
    private String UserName;

    @Column(name="class_name")
    protected String type;

    public Person(){}

    public Person(String firstname, String lastname, String Mail, String Password, String UserName){
        super();
        this.firstname=firstname;
        this.lastname=lastname;
        this.Mail=Mail;
        this.Password=Password;
        this.UserName=UserName;
        this.type="Person";
    }

    public int getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMail() {
        return Mail;
    }

    public String getPassword() {
        return Password;
    }

    public String getUserName() {
        return UserName;
    }

    public String getType(){
        return this.type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setMail(String mail) {
        Mail = mail;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public void setType(String type) {
        this.type = type;
    }
}

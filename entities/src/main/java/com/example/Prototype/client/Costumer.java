package com.example.Prototype.client;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "costumers")
public class Costumer implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @NotNull
    private String email;

    @NotNull
    private String first_name;

    @NotNull
    private String last_name;

    private String verification_code;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "costumer")
    private List<Ticket> tickets;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "costumer")
    private List<ComplaintReport> complaints;

    public Costumer(){
        tickets=new ArrayList<Ticket>();
        complaints=new ArrayList<ComplaintReport>();
    }

    public Costumer(String id, String first_name, String last_name, String email){
        this.id=id;
        this.email=email;
        this.first_name=first_name;
        this.last_name=last_name;
        tickets=new ArrayList<Ticket>();
        complaints=new ArrayList<ComplaintReport>();
    }

    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getVerification_code() {
        return verification_code;
    }

    public void setVerification_code(String verification_code) {
        if(verification_code ==null){
            this.verification_code=null;
            return;
        }
        if(verification_code.length()!=4)
            return;
        for(int i=0 ; i < 4; i++)
            if(verification_code.charAt(i) < '0' || verification_code.charAt(i) > '9')
                return;
        this.verification_code = verification_code;
    }

    public void sendMail(String subject, String text){
        SendMail sendMail=new SendMail(this.email, subject, text);
        sendMail.main(null);
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public List<ComplaintReport> getComplaints() {
        return complaints;
    }

    public void setComplaints(List<ComplaintReport> complaints) {
        this.complaints = complaints;
    }

    public void addComplaint(ComplaintReport complaint){
        this.complaints.add(complaint);
    }

    public void deleteComplaint(ComplaintReport complaint){
        this.complaints.remove(complaint);
    }

    public void addTicket(Ticket ticket){
        this.tickets.add(ticket);
    }

    public void deleteTicket(Ticket ticket){
        this.tickets.remove(ticket);
    }
}

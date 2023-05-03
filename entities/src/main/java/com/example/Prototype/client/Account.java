package com.example.Prototype.client;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "accounts")
public class Account implements Serializable {
    @Id
    @Column(name = "id", nullable = false)
    private String accountNum;

    @NotNull
    private String end_month;

    @NotNull
    private String end_year;

    @NotNull
    private String cvv;

    @NotNull
    @Column(name = "balance")
    private double balance;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "purchase_account")
    private List<Ticket> tickets;

    public Account(){
        this.tickets=new ArrayList<Ticket>();
    }

    public Account(String accountNum, String end_month, String end_year, String cvv, int balance) {
        this.accountNum=accountNum;
        this.end_month=end_month;
        this.end_year=end_year;
        this.cvv=cvv;
        this.balance = balance;
        this.tickets=new ArrayList<Ticket>();
    }


    public String getAccountNum() {
        return accountNum;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void debit(double price){
        this.balance-=price;
    }

    public void credit(double _credit){
        this.balance+=_credit;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }

    public void addTicket(Ticket ticket){
        this.tickets.add(ticket);
    }

    public void deleteTicket(Ticket ticket){
        this.tickets.remove(ticket);
    }

    public boolean isValidate(double price){
        return price <= balance;
    }

    public boolean isIdentical(String end_month, String end_year, String cvv){
        return this.end_month.equals(end_month) && this.end_year.equals(end_year) && this.cvv.equals(cvv);
    }
}

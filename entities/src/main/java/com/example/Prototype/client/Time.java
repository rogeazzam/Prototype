package com.example.Prototype.client;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="times")
public class Time implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int day;
	
	private int month;
	
	private int year;
	
	private String begTime;
	
	private String endTime;

	@ManyToOne
	@JoinColumn(name = "movie_id")
	private Movie movie;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "hall_id")
	private Hall hall;

	@OneToOne(
			cascade = {CascadeType.ALL}
	)
	@JoinColumn(
			name = "movie_map",
			referencedColumnName = "id"
	)
	private Map map;

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "details")
	private List<Ticket> tickets;

	private int availableSeats=80;

	public Time(){
		super();
		this.day=1;
		this.month=1;
		this.year=2000;
		this.begTime="00:00";
		this.endTime="02:00";
		this.tickets=new ArrayList<Ticket>();
	}
	
	public Time(int day,int month,int year,String begTime,String endTime){
		this.day=day;
		this.month=month;
		this.year=year;
		this.begTime=begTime;
		this.endTime=endTime;
		this.tickets=new ArrayList<Ticket>();
	}

	public int getId(){
		return id;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public String getBegTime(){
		return this.begTime;
	}

	public void setBegTime(String begTime){
		this.begTime=begTime;
	}

	public String getEndTime(){
		return this.endTime;
	}

	public void setEndTime(String endTime){
		this.endTime=endTime;
	}

	public void setMovie(Movie movie){
		this.movie=movie;
	}

	public Movie getMovie(){
		return this.movie;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	public Hall getHall() {
		return hall;
	}

	public Map getMap(){
		return this.map;
	}

	public void setMap(Map map){
		this.map=map;
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

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public boolean greater(Time other){
		if(this.year != other.getYear())
			return this.year > other.getYear();
		if(this.month != other.getMonth())
			return this.month > other.getMonth();
		if(this.day != other.getDay())
			return this.day > other.getDay();
		String[] arr1=begTime.split(":");
		int hour=Integer.parseInt(arr1[0]);
		int minute=Integer.parseInt(arr1[1]);
		String[] arr2=other.getBegTime().split(":");
		int other_hour=Integer.parseInt(arr2[0]);
		int other_minute=Integer.parseInt(arr2[1]);
		if(hour != other_hour)
			return hour > other_hour;
		return minute > other_minute;
	}
}

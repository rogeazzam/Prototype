package com.example.Prototype.client;

import javax.persistence.*;

@Entity
@Table(name="times")
public class Time {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private int day;
	
	private int month;
	
	private int year;
	
	private String begTime;
	
	private String endTime;

	@OneToOne(
			mappedBy = "screeningTime"
	)
	private Movie movie;

	public Time(){
		super();
		this.day=1;
		this.month=1;
		this.year=2000;
		this.begTime="00:00";
		this.endTime="02:00";
	}
	
	public Time(int day,int month,int year,String begTime,String endTime){
		this.day=day;
		this.month=month;
		this.year=year;
		this.begTime=begTime;
		this.endTime=endTime;
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
}

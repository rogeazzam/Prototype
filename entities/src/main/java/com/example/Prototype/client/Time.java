package com.example.Prototype.client;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "times")
public class Time {

	private int day;
	
	private int month;
	
	private int year;
	
	private String begHour;
	
	private String begMinute;
	
	private String endHour;
	
	private String endMinute;
	
	public Time(int day,int month,int year,String begHour,String begMinute,String endHour,String endMinute){
		this.day=day;
		this.month=month;
		this.year=year;
		this.begHour=begHour;
		this.begMinute=begMinute;
		this.endHour=endHour;
		this.endMinute=endMinute;
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

	public String getBegHour() {
		return begHour;
	}

	public void setBegHour(String begHour) {
		this.begHour = begHour;
	}

	public String getBegMinute() {
		return begMinute;
	}

	public void setBegMinute(String begMinute) {
		this.begMinute = begMinute;
	}

	public String getEndHour() {
		return endHour;
	}

	public void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	public String getEndMinute() {
		return endMinute;
	}

	public void setEndMinute(String endMinute) {
		this.endMinute = endMinute;
	}
}

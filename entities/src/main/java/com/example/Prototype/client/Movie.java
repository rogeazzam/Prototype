package com.example.Prototype.client;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "movie")
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    private String name;
    private String Actor;
    private String Director;
	@Column(name = "image_source")
    private String ImgSrc;
	@Column(name = "description")
    private String Text;
    
    @Column(name="movie_time")
    private Time screeningTime;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "list_id")
    private MovieList list;
    
    Movie(){
    }
    
    public Movie(String name,String Actor, String Director, String ImgSrc,String Text,Time time){
    	this.name=name;
    	this.Actor=Actor;
    	this.Director=Director;
    	this.ImgSrc=ImgSrc;
    	this.Text=Text;
    	this.screeningTime=time;
    }
    
    public void setName(String x) {
    	this.name=x;
    }
    
    public void setActor(String x) {
    	this.Actor=x;
    }
    
    public void setDirector(String x) {
    	this.Director=x;
    }
    
    public void setImgSrc(String x) {
    	this.ImgSrc=x;
    }
    
    public String getName() {
    	return this.name;
    }
    
    public String getActor() {
    	return this.Actor;
    }
    
    public String getDirector() {
    	return this.Director;
    }
    
    public String getImgSrc() {
    	return this.ImgSrc;
    }

	public String getText() {
		return Text;
	}

	public void setText(String text) {
		Text = text;
	}

	public Time getScreeningTime() {
		return screeningTime;
	}

	public void setScreeningTime(Time screeningTime) {
		this.screeningTime = screeningTime;
	}
}

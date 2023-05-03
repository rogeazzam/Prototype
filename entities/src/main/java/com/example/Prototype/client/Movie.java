package com.example.Prototype.client;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "movie")
public class Movie implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    @Column(name = "movie_name")
    private String name;
    @Column(name = "actor_name")
    private String Actor;
    @Column(name = "director_name")
    private String Director;
	@Column(name = "image_source")
    private String ImgSrc;
	@Column(name = "description")
    private String Text;

    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "movie", orphanRemoval = true)
    private List<Time> screeningTime;

   /* @ManyToMany(
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            targetEntity = Person.class
    )
    @JoinTable(
            name = "movie_halls",
            joinColumns = {@JoinColumn(
                    name = "movie_id"
            )},
            inverseJoinColumns = {@JoinColumn(
                    name = "hall_id"
            )}
    )
    private List<Hall> halls;*/

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "movies")
    private List<MovieList> lists;
    
    Movie(){
    }
    
    public Movie(String name,String Actor, String Director, String ImgSrc,String Text){
    	this.name=name;
    	this.Actor=Actor;
    	this.Director=Director;
    	this.ImgSrc=ImgSrc;
    	this.Text=Text;
    	this.screeningTime=new ArrayList<Time>();
    	this.lists=new ArrayList<MovieList>();
    	//this.screeningTime=time;
    }
    public int getId() {
        return this.id;
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

	public List<Time> getScreeningTime() {
		return screeningTime;
	}

	public void setScreeningTime(Time screeningTime) {
		this.screeningTime.add(screeningTime);
	}

	public List<MovieList> getLists(){
        return this.lists;
    }

    public void setLists(List<MovieList> lists) {
        this.lists = lists;
    }

    public void setList(MovieList list){
        this.lists.add(list);
    }

    /*public List<Hall> getHalls() {
        return halls;
    }

    public void setHalls(List<Hall> halls) {
        this.halls = halls;
    }*/
}

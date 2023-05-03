package com.example.Prototype.client;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "branches")
public class Branch implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Column(name="branch_name", unique = true)
	private String name;

	@Column(name="branch_imagesrc")
	private String ImgSrc;
	
	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "movies_id")
	private MovieList movies;
	
	@Column(name="branch_city")
	private String City;
	
	@Column(name="branch_address")
	private String StreetAddress;

	@OneToOne(
			cascade = {CascadeType.ALL}
	)
	@JoinColumn(
			name = "branch_manager",
			referencedColumnName = "id"
	)
	private Person manager;

	@ManyToMany(cascade = CascadeType.ALL, mappedBy = "branches")
	private List<BranchesList> lists;

	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER, mappedBy = "branch")
	private List<Hall> halls;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "branch")
	private List<Person> employees;

	public Branch(){
		this.lists=new ArrayList<BranchesList>();
		this.halls=new ArrayList<Hall>();
		this.employees=new ArrayList<Person>();
	}
	
	public Branch(String name,String ImgSrc,String City,String StreetAddress){
		this.name=name;
		this.ImgSrc=ImgSrc;
		this.City=City;
		this.StreetAddress=StreetAddress;
		this.movies=new MovieList();
		this.lists=new ArrayList<BranchesList>();
		this.halls=new ArrayList<Hall>();
		this.employees=new ArrayList<Person>();
	}

	public int getId(){
		return this.id;
	}

	public String getStreetAddress() {
		return StreetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		StreetAddress = streetAddress;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public MovieList getMovie() {
		return movies;
	}

	public void setMovie(MovieList movies) {
		this.movies=movies;
	}

	public String getImgSrc() {
		return ImgSrc;
	}

	public void setImgSrc(String imgSrc) {
		ImgSrc = imgSrc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Hall> getHalls() {
		return halls;
	}

	public void setHalls(List<Hall> halls) {
		this.halls = halls;
	}

	public void setHall(Hall hall){
		this.halls.add(hall);
	}

	public List<BranchesList> getLists() {
		return lists;
	}

	public void setLists(List<BranchesList> lists) {
		this.lists = lists;
	}

	public void addBranchesList(BranchesList branchesList){
		this.lists.add(branchesList);
	}

	public Person getManager() {
		return manager;
	}

	public void setManager(BranchManager manager) {
		this.manager = manager;
	}

	public List<Person> getEmployees() {
		return employees;
	}

	public void setEmployees(List<Person> employees) {
		this.employees = employees;
	}

	public void addEmployee(Employee employee){
		this.employees.add(employee);
	}
}

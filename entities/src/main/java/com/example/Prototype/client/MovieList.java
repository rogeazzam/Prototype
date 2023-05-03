package com.example.Prototype.client;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "movies")
public class MovieList implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "movielists",
			joinColumns = { @JoinColumn(name = "movielist_id") },
			inverseJoinColumns = { @JoinColumn(name = "movie_id") }
	)
	private List<Movie> movies=new ArrayList<Movie>();
	private int size=0;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "movies")
	private List<Branch> branches;

	@OneToOne(
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY,
			mappedBy = "movies"
	)
	private HomeWatch homeWatch;
	
	public MovieList(){
		this.movies=new ArrayList<Movie>();
		branches=new ArrayList<Branch>();
	}
	
	public MovieList(MovieList movies){
		this.movies=movies.getMovies();
		this.branches=movies.getBranches();
		this.size=movies.getSize();
	}

	public List<Movie> getMovies() {
		return movies;
	}

	public void setMovies(Movie movie) {
		this.movies.add(movie);
		this.size++;
	}

	public void setMovies(List<Movie> movieList) {
		this.movies=movieList;
		this.size=movieList.size();
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public List<Branch> getBranches() {
		return branches;
	}

	public void setBranch(Branch branch) {
		branches.add(branch);
	}

	public HomeWatch getHomeWatch() {
		return homeWatch;
	}

	public void setHomeWatch(HomeWatch homeWatch) {
		this.homeWatch = homeWatch;
	}
}

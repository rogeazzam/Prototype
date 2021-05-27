package com.example.Prototype.client;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "movies")
public class MovieList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
    
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "movie")
	private List<Movie> movies=new ArrayList<Movie>();
	private int size=0;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "movies")
	private List<Branch> branches;
	
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
}

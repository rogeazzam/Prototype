package com.example.Prototype.client;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "branches")
public class Branch {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Column(name="branch_name")
	private String name;

	@Column(name="branch_imagesrc")
	private String ImgSrc;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "movies_id")
	private MovieList movies;
	
	@Column(name="branch_city")
	private String City;
	
	@Column(name="branch_address")
	private String StreetAddress;
	
	public Branch(String name,String ImgSrc,String City,String StreetAddress){
		this.name=name;
		this.ImgSrc=ImgSrc;
		this.City=City;
		this.StreetAddress=StreetAddress;
		this.movies=new MovieList();
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
		
		/*List<Movie> list=movies.getMovies();
		
		for(int i=0;i<list.size();i++) {
			this.movies.setMovies(list.get(i));
		}*/
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
}

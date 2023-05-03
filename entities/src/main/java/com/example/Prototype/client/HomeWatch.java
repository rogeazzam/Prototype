package com.example.Prototype.client;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name= "homewatch")
public class HomeWatch implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(
            cascade = CascadeType.ALL
    )
    @JoinColumn(
            name = "home_movies",
            referencedColumnName = "id"
    )
    private MovieList movies;

    public HomeWatch(){}

    public HomeWatch(MovieList movies){
        this.movies=movies;
    }

    public void setMovies(MovieList movies) {
        this.movies = movies;
    }

    public MovieList getMovies(){
        return movies;
    }
}

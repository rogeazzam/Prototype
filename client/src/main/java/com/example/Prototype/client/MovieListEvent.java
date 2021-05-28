package com.example.Prototype.client;

public class MovieListEvent {
    private MovieList movies;

    public MovieList getMovies(){
        return movies;
    }

    public MovieListEvent(MovieList movies){
        this.movies=movies;
    }
}

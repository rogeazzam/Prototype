package com.example.Prototype.client;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ShowMovieController {

    @FXML
    private ImageView movieImg;

    @FXML
    private Label movieText;

    @FXML
    private Label movieName;
    
    private Movie movie;
    
    public void setData(Movie movie) {
    	this.movie=movie;
		movieName.setText(movie.getName());
		Image image=new Image(getClass().getResourceAsStream(movie.getImgSrc()));
		movieImg.setImage(image);
		movieText.setText(movie.getText());
    	
    }

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

}

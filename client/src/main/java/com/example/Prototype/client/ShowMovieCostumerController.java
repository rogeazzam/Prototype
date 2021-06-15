package com.example.Prototype.client;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ShowMovieCostumerController {

    @FXML
    private Label movieName;

    @FXML
    private ImageView movieImg;

    @FXML
    private Label movieText;

    @FXML
    private GridPane grid;

    private Movie movie;

    @FXML
    private Label screeningTimeLabel;

    public void setData(Movie movie){
        this.movie=movie;
        movieName.setText(movie.getName());
        Image image=new Image(getClass().getResourceAsStream(movie.getImgSrc()));
        movieImg.setImage(image);
        movieText.setText(movie.getText());
        screeningTimeLabel.setText("Date of screening: " + movie.getScreeningTime().getDay()
                + "/" + movie.getScreeningTime().getMonth()+ "/" + movie.getScreeningTime().getYear()+ "   "
                + "At " + movie.getScreeningTime().getBegTime());
    }
}

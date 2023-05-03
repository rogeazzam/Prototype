package com.example.Prototype.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AvailableSeatsController {

    @FXML
    private Label screeningTimeLabel;

    private Movie movie;

    public void setData(Movie movie){
        this.movie=movie;
        screeningTimeLabel.setText("");
        screeningTimeLabel.setWrapText(true);
        for (int i=0; i < movie.getScreeningTime().size(); i++) {
            if(movie.getScreeningTime().get(i).getHall() != null) {
                String Date = movie.getScreeningTime().get(i).getDay()
                        + "/" + movie.getScreeningTime().get(i).getMonth() + "/" + movie.getScreeningTime().get(i).getYear();

                screeningTimeLabel.setText(screeningTimeLabel.getText() + "Date of screening: " + Date + "   "
                        + "At " + movie.getScreeningTime().get(i).getBegTime() + " |Available Seats:" +
                                String.valueOf(movie.getScreeningTime().get(i).getAvailableSeats()) + "\n");
            }
        }
    }
}

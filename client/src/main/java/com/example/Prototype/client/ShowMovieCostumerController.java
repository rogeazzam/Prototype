package com.example.Prototype.client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.io.IOException;

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

    int seatChosen[];

    Button seats[];

    @FXML
    private Label screeningTimeLabel;

    public void setData(Movie movie) throws IOException {
        this.movie=movie;
        movieName.setText(movie.getName());
        Image image=new Image(getClass().getResourceAsStream(movie.getImgSrc()));
        movieImg.setImage(image);
        movieText.setText(movie.getText());
        screeningTimeLabel.setText("");
        screeningTimeLabel.setWrapText(true);
        for (int i=0; i < movie.getScreeningTime().size(); i++) {
            if(movie.getScreeningTime().get(i).getMap()!=null) {
                String Date = movie.getScreeningTime().get(i).getDay()
                        + "/" + movie.getScreeningTime().get(i).getMonth() + "/" + movie.getScreeningTime().get(i).getYear();
                screeningTimeLabel.setText(screeningTimeLabel.getText() + "Date of screening: " + Date + "   "
                        + "At " + movie.getScreeningTime().get(i).getBegTime()+" Branch: " + movie.getScreeningTime().get(i).getHall().getBranch().getName()
                            + "    Hall: " + movie.getScreeningTime().get(i).getHall().getName() + "\n");
            }
        }
    }

    @FXML
    void backOP(ActionEvent event) {
        App.myStage.setScene(SecondaryController.saveScene);
        App.myStage.setMaximized(true);
        App.myStage.show();
    }

}

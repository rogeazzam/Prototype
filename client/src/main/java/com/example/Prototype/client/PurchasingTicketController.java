package com.example.Prototype.client;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class PurchasingTicketController {
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
        screeningTimeLabel.setWrapText(true);
        for (int i=0; i < movie.getScreeningTime().size(); i++) {
            screeningTimeLabel.setText(screeningTimeLabel.getText() + "Date of screening: " + movie.getScreeningTime().get(i).getDay()
                    + "/" + movie.getScreeningTime().get(i).getMonth() + "/" + movie.getScreeningTime().get(i).getYear() + "   "
                    + "At " + movie.getScreeningTime().get(i).getBegTime() + "\n");
        }

        int col=0,row=1,count=0;
        seatChosen=new int[80];
        for(int i=0;i<80;i++)
            seatChosen[i]=0;
        seats=new Button[80];

        for(int i = 0; i < 80 ; i++){
            if(col==8){
                col=0;
                row++;
            }
            seats[i]=new Button();
            seats[i].setMinWidth(40);
            seats[i].setStyle("-fx-background-color: #800020");
            int finalI = i;
            seats[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    System.out.println("hii");
                    if(seatChosen[finalI]==0) {
                        seatChosen[finalI] = 1;
                        seats[finalI].setStyle("-fx-background-color: #8E8E8E");
                    }
                    else if(seatChosen[finalI]==1) {
                        seatChosen[finalI] = 0;
                        seats[finalI].setStyle("-fx-background-color: #800020");
                    }
                }
            });

            grid.add(seats[i],col++,row);
            GridPane.setMargin(seats[i], new Insets(0,0,10,10));
        }
    }
}

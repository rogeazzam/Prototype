package com.example.Prototype.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeTicketController {

    @FXML
    private Label movieName;

    @FXML
    private ImageView movieImg;

    @FXML
    private Label movieTimes;

    @FXML
    private ChoiceBox<Integer> ticketNum;


    private Movie movie;

    private Time open_time;

    public void setData(Movie movie){
        this.movie=movie;
        Image image=new Image(getClass().getResourceAsStream(movie.getImgSrc()));

        for(Integer i=0; i < 10; i++){
            ticketNum.getItems().add(i+1);
        }
        ticketNum.setValue(1);

        movieImg.setImage(image);
        movieTimes.setText("");
        movieTimes.setWrapText(true);
        List<Time> times=movie.getScreeningTime();
        for(Time time:times){
            if(time.getMap()==null){
                open_time=time;
                movieTimes.setText( movieTimes.getText()+String.valueOf(time.getDay()) + "/" + String.valueOf(time.getMonth())
                        + "/" + String.valueOf(time.getYear()) + "   "+ "At" + time.getBegTime()+"\n");
                break;
            }
        }
    }

    @FXML
    void backOP(ActionEvent event) {
        App.myStage.setScene(SecondaryController.saveScene);
        App.myStage.setMaximized(true);
        App.myStage.show();
    }


    @FXML
    void purchaseBtn(ActionEvent event) throws IOException {
        EventBus.getDefault().unregister(this);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("creditcard.fxml"));
        Parent root = loader.load();

        CreditCardController itemController = loader.getController();
        int[] num={ticketNum.getValue()};
        itemController.setData(open_time, num, true);

        App.myStage.setScene(new Scene(root));
        App.myStage.setMaximized(true);
        App.myStage.show();
    }
}

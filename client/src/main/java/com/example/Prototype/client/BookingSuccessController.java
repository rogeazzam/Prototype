package com.example.Prototype.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;

import java.io.IOException;

public class BookingSuccessController {

    public void setData(){
    }

    @FXML
    void backOP(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
        Parent root = loader.load();

        SecondaryController itemController = loader.getController();
        itemController.setData();

        Scene newscene = new Scene(root);
        App.myStage.setScene(newscene);
        App.myStage.setMaximized(true);
        App.myStage.show();
    }
}
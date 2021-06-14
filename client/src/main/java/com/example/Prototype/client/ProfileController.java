package com.example.Prototype.client;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

public class ProfileController {

    Person person;

    @FXML
    private Label welcomeUser;

    @FXML
    private GridPane gridButtons;

    @FXML
    private GridPane processGrid;

    public void setData(Person person){
        gridButtons=person.getButtons();
    }

}

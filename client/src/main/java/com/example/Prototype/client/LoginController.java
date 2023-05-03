package com.example.Prototype.client;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class LoginController {

    @FXML
    private TextField UsernameTxt;

    @FXML
    private PasswordField PasswordTxt;

    @FXML
    public void CheckLogin(javafx.event.ActionEvent actionEvent) throws IOException {
        SimpleClient.getClient().sendToServer("#LOGIN"+" "+UsernameTxt.getText()+" "+PasswordTxt.getText());
    }

    public void setData(){}
}

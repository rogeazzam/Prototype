package com.example.Prototype.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;

public class FillComplaintController {
    @FXML
    private TextField subjectText;

    @FXML
    private TextArea msgText;

    @FXML
    private Label errorMsg;

    @FXML
    private TextField creditCardNum;

    @FXML
    private TextField validityMonth;

    @FXML
    private TextField validityYear;

    @FXML
    private TextField cvvNum;

    private Costumer costumer;

    public void setData(Costumer costumer){
        this.costumer=costumer;
        msgText.setWrapText(true);
        msgText.setText("");
    }

    @FXML
    void sendOP(ActionEvent event) throws IOException {
        if(msgText.getText().equals(""))
            errorMsg.setVisible(true);
        SimpleClient.getClient().sendToServer("#sendComplaint%%" + subjectText.getText() + "%%"
                         + msgText.getText() + "%%" + costumer.getId() + "%%" + creditCardNum.getText() + "%%" +
                            validityMonth.getText() + "%%" + validityYear.getText() + "%%" + cvvNum.getText());
    }
}

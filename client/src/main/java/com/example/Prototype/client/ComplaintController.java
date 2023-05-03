package com.example.Prototype.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ComplaintController {
    @FXML
    private TextArea replyTxt;

    @FXML
    private Label subLabel;

    @FXML
    private Label txtLabel;

    @FXML
    private TextField refundTxt;

    private ComplaintReport complaint;

    public void setData(ComplaintReport complaint){
        this.complaint=complaint;
        this.refundTxt.setText("");
        subLabel.setText(complaint.getSubject());
        txtLabel.setText(complaint.getText());
    }

    @FXML
    void refundOP(ActionEvent event) throws IOException {
        SimpleClient.getClient().sendToServer("#ComplaintRefund%%" + refundTxt.getText() +
                            "%%" + String.valueOf(complaint.getId()));
    }

    @FXML
    void replyOP(ActionEvent event) throws IOException {
        SimpleClient.getClient().sendToServer("#replyToComplaint%%" + replyTxt.getText() +
                            "%%" + subLabel.getText() + "%%" + String.valueOf(complaint.getId()));
    }
}

package com.example.Prototype.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class PurplePassController {

    @FXML
    private CheckBox check1;

    @FXML
    private DatePicker startDate;

    @FXML
    private DatePicker endDate;

    @FXML
    private TextField maxSeats;

    @FXML
    private CheckBox check2;

    @FXML
    private CheckBox check3;

    @FXML
    private Label errorMsg;

    public void setData(){
    }

    @FXML
    void chosen1(ActionEvent event) {
        check2.setSelected(false);
        check3.setSelected(false);
    }

    @FXML
    void chosen2(ActionEvent event) {
        check1.setSelected(false);
        check3.setSelected(false);
    }

    @FXML
    void chosen3(ActionEvent event) {
        check1.setSelected(false);
        check2.setSelected(false);
    }

    @FXML
    void updateOP(ActionEvent event) throws IOException {
        if(!check1.isSelected() && !check2.isSelected() && !check3.isSelected()) {
            errorMsg.setText("Choose Option!");
            return;
        }
        if(check3.isSelected())
            SimpleClient.getClient().sendToServer("#UpdatePurplePass" + "%%" + "a" + "%%" + "a" + "%%" + String.valueOf(-1));
        else {
            LocalDate localDate = startDate.getValue();
            Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date1 = Date.from(instant);
            String pattern = "MM-dd-yyyy HH:mm:ss";

            DateFormat df = new SimpleDateFormat(pattern);
            String str1 = df.format(date1);

            localDate = endDate.getValue();
            instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
            Date date2 = Date.from(instant);

            String str2 = df.format(date2);
            if (check1.isSelected())
                SimpleClient.getClient().sendToServer("#UpdatePurplePass" + "%%" + str1 + "%%" + str2 + "%%" + maxSeats.getText());
            else if (check2.isSelected())
                SimpleClient.getClient().sendToServer("#UpdatePurplePass" + "%%" + str1 + "%%" + str2 + "%%" + String.valueOf(0));
        }

        Stage stage = (Stage) maxSeats.getScene().getWindow();
        stage.close();
    }
}

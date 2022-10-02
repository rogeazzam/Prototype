package com.example.Prototype.client;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.File;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class AddMovieController {

    @FXML
    private TextField name;

    @FXML
    private ChoiceBox<?> startHour;

    @FXML
    private ChoiceBox<?> startMinute;

    @FXML
    private ChoiceBox<?> endHour;

    @FXML
    private ChoiceBox<?> endMinute;

    @FXML
    private TextField branch;

    @FXML
    private TextField hall;

    @FXML
    private TextArea description;

    @FXML
    private Button save;

    @FXML
    private TextField actor;

    @FXML
    private TextField director;

    @FXML
    private Label errorMsg;

    @FXML
    private TextField imageSrc;

    @FXML
    private DatePicker date;

    @FXML
    private Button exitBtn;

    final FileChooser fileChooser = new FileChooser();

    @FXML
    void saveOP(ActionEvent event) throws IOException {
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        String dateString = date.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        SimpleClient.getClient().sendToServer("#AddMovie"+name.getText()+"%%"+dateString+"%%"+String.valueOf(startHour.getValue())
            +":"+String.valueOf(startMinute.getValue())+"%%"+String.valueOf(endHour.getValue())+":"+String.valueOf(endMinute.getValue())
                +"%%"+branch.getText()+"%%"+hall.getText()+"%%"+actor.getText()+"%%"+director.getText()+"%%"+imageSrc.getText()
                +"%%"+description.getText());
    }

    @Subscribe
    public void ErrorFound(StringReceiver str){
        Platform.runLater(()-> {
            errorMsg.setText(str.getStr());
        });
    }

    public void setData(){
        ObservableList list= FXCollections.observableArrayList();
        list.clear();
        for(Integer i=0;i<24;i++) {
            String str;
            if (i < 10)
                str = "0" + String.valueOf(i);
            else
                str=String.valueOf(i);
            list.add(str);
        }
        startHour.getItems().addAll(list);
        endHour.getItems().addAll(list);
        list.clear();
        for(Integer i=0;i<60;i++) {
            String str;
            if (i < 10)
                str = "0" + String.valueOf(i);
            else
                str=String.valueOf(i);
            list.add(str);
        }
        startMinute.getItems().addAll(list);
        endMinute.getItems().addAll(list);
    }

    @FXML
    void exitOp(ActionEvent event) {
        if(EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
        Stage stage = (Stage) exitBtn.getScene().getWindow();
        stage.close();
    }

}

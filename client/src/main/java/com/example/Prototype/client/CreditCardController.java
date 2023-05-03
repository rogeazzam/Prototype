package com.example.Prototype.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreditCardController {

    @FXML
    private TextField creditCardNum;

    @FXML
    private TextField validityMonth;

    @FXML
    private TextField validityYear;

    @FXML
    private TextField cvvNum;

    @FXML
    private TextField IDText;

    @FXML
    private TextField FirstNameTxt;

    @FXML
    private TextField LastNameTxt;

    @FXML
    private Label priceLabel;

    @FXML
    private TextField emailText;

    @FXML
    private Label errorMsg;

    private int index;

    private Time time;

    private int seatChosen[];

    private double price;

    private int enterNum;

    private boolean homeTicket=false;

    private String toSend="";

    public void setData(Time time, int seatChosen[]) {
        this.enterNum = 0;
        this.time = time;
        this.seatChosen = seatChosen;
        if(!homeTicket && seatChosen != null) {
            Map map = time.getMap();
            price = 0;

            for (int i = 0; i < 80; i++) {
                if (seatChosen[i] == 1) {
                    price += time.getTickets().get(i).getPrice();
                }
            }
            priceLabel.setText((Double.toString(price)));
        }else if(homeTicket){
            price = seatChosen[0]*time.getTickets().get(time.getTickets().size()-1).getPrice();
            priceLabel.setText(Double.toString(price));
        }else{
            priceLabel.setText(String.valueOf(MultipleTickets.price));
        }
    }

    public void setData(Time time, int seatChosen[], boolean homeTicket) {
        this.homeTicket=homeTicket;
        setData(time, seatChosen);
    }

    public void setData(String toSend) throws IOException {
        this.toSend = toSend;
        priceLabel.setText(String.valueOf(MultipleTickets.price));
    }

    @FXML
    void CheckCredit(ActionEvent event) throws IOException {
        this.enterNum++;
            if (!EventBus.getDefault().isRegistered(this))
                EventBus.getDefault().register(this);
            if(this.toSend != "") {
                this.toSend = "#creditCardCheck" + "%%" + creditCardNum.getText() + "%%"
                        + validityMonth.getText() + "%%" + validityYear.getText() + "%%" + cvvNum.getText() + "%%" + IDText.getText()
                        + "%%" + FirstNameTxt.getText() + "%%" + LastNameTxt.getText() + "%%" + emailText.getText() + "%%" +
                        String.valueOf(MultipleTickets.price) + "%%" + this.toSend;
                SimpleClient.getClient().sendToServer(toSend);
                return;
            }
            String seats_str="";
            int len=seatChosen.length;
            for(int i=0; i < len; i++) {
                if (seatChosen[i] == 1) {
                    seats_str += String.valueOf(i) + ",";
                }
            }
            if(homeTicket) {
                seats_str = String.valueOf(seatChosen[0]);
                seats_str+=",";
            }
            seats_str+=String.valueOf(time.getId());
            String toSend="#creditCardCheck" + "%%" + creditCardNum.getText() + "%%"
                    + validityMonth.getText() + "%%" + validityYear.getText() + "%%" + cvvNum.getText() + "%%" + IDText.getText()
                    + "%%" + FirstNameTxt.getText() + "%%" + LastNameTxt.getText() + "%%" + emailText.getText() + "%%" +
                    Double.toString(price) + "%%" + seats_str;
            if(homeTicket)
                toSend+="%%" + "...";
            SimpleClient.getClient().sendToServer(toSend);
    }

    @FXML
    public void cancelOrder(ActionEvent actionEvent) throws IOException {
        try{
            Map map = time.getMap();
            for (int i = 0; i < 80; i++) {
                if (seatChosen[i] == 1){
                    seatChosen[i]=0;
                    time.getMap().getSeats().get(i).setStatus(0);
                }
            }
            SimpleClient.getClient().sendToServer(time);

            EventBus.getDefault().unregister(this);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
            Parent root = loader.load();

            SecondaryController itemController = loader.getController();
            itemController.setData();

            Scene newscene = new Scene(root);
            App.myStage.setScene(newscene);
            App.myStage.setMaximized(true);
            App.myStage.show();
        } catch(IOException e)
        {
           e.printStackTrace();
        }
    }

    @Subscribe
    public void costumer(StringReceiver str){
            Platform.runLater(() -> {
                    try {
                        if(str.getStr().equals("#creditCardError") || str.getStr().equals("#noEnoughCredit")) {
                            if (this.enterNum > 5)
                                cancelOrder(new ActionEvent());
                            errorMsg.setText(str.getStr());
                        }else if(str.getStr().equals("#found")){
                            FXMLLoader loader = new FXMLLoader(getClass().getResource("bookingsuccess.fxml"));
                            Parent root = loader.load();

                            BookingSuccessController itemController = loader.getController();
                            itemController.setData();

                            Scene newscene = new Scene(root);
                            App.myStage.setScene(newscene);
                            App.myStage.setMaximized(true);
                            App.myStage.show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            });
    }
}

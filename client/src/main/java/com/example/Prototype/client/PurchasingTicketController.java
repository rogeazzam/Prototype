package com.example.Prototype.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PurchasingTicketController {
    @FXML
    private Label movieName;

    @FXML
    private ImageView movieImg;

    @FXML
    private Label movieText;

    @FXML
    private GridPane grid;

    @FXML
    private ChoiceBox<String> chooseTime;

    @FXML
    private ChoiceBox<Integer> numChoice;

    @FXML
    private Label errorMsg;

    @FXML
    private Button chooseSeats;

    private Movie movie;

    int seatChosen[];

    Button seats[];

    private List<Seat> seatList=new ArrayList<Seat>();

    private Time time;

    private String branch_name;

    boolean seat_found=false;

    private int index=0;

    private double maxSeats;

    @FXML
    private Label screeningTimeLabel;

    public void setData(Movie movie, String name) throws IOException {
        this.movie=movie;
        this.branch_name=name;

        for (int j = 1; j <= 10; j++)
            numChoice.getItems().add(j);
        numChoice.setValue(1);

        movieName.setText(movie.getName());
        Image image=new Image(getClass().getResourceAsStream(movie.getImgSrc()));
        movieImg.setImage(image);
        movieText.setText(movie.getText());
        screeningTimeLabel.setText("");
        screeningTimeLabel.setWrapText(true);
        for (int i=0; i < movie.getScreeningTime().size(); i++) {
            Hall hall=movie.getScreeningTime().get(i).getHall();
            if(hall!=null && hall.getBranch().getName().equals(this.branch_name)) {
                String Date = movie.getScreeningTime().get(i).getDay()
                        + "/" + movie.getScreeningTime().get(i).getMonth() + "/" + movie.getScreeningTime().get(i).getYear();
                screeningTimeLabel.setText(screeningTimeLabel.getText() + "Date of screening: " + Date + "   "
                        + "At " + movie.getScreeningTime().get(i).getBegTime() + "\n");
                chooseTime.getItems().add(Date + " " + movie.getScreeningTime().get(i).getBegTime());
            }
        }
        chooseTime.setValue(chooseTime.getItems().get(0));
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        Time time=movie.getScreeningTime().get(0);
        SimpleClient.getClient().sendToServer("#PurplePass" + String.valueOf(time.getMonth()) + "-" +
                String.valueOf(time.getDay()) + "-" + String.valueOf(time.getYear()) + " " +
                    time.getBegTime() + ":00");
    }

    @FXML
    void backOP(ActionEvent event) {
        App.myStage.setScene(SecondaryController.saveScene);
        App.myStage.setMaximized(true);
        App.myStage.show();
    }


    @FXML
    void timeChange(ActionEvent event) throws IOException {
        int time_index=0;
        List<String> list=chooseTime.getItems();
        for(String str:list) {
            if (chooseTime.getValue().equals(str))
                break;
            time_index++;
        }
        errorMsg.setVisible(false);
        index=time_index;
        Time temp_time=movie.getScreeningTime().get(time_index);
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        SimpleClient.getClient().sendToServer("#PurplePass" + String.valueOf(temp_time.getMonth()) + "-" +
                String.valueOf(temp_time.getDay()) + "-" + String.valueOf(temp_time.getYear()) + " " +
                temp_time.getBegTime() + ":00");
    }


    @FXML
    void purchaseHandle(ActionEvent event) throws IOException {
        List<Time> list=movie.getScreeningTime();
        int index=0;
        for(Time time:list){
            String Date = time.getDay()
                    + "/" + time.getMonth() + "/" + time.getYear();
            screeningTimeLabel.setText(screeningTimeLabel.getText() + "Date of screening: " + Date + "   "
                    + "At " + time.getBegTime() + "\n");
            String str= Date + " " + time.getBegTime();
            if(chooseTime.getValue().equals(str))
                break;
            index++;
        }
        time=movie.getScreeningTime().get(index);
        Map map=movie.getScreeningTime().get(index).getMap();
        for(int i=0;i<80;i++) {
            if (seatChosen[i] == 1) {
                seat_found=true;
                Seat seat = map.getSeats().get(i);
                if (seat.getStatus() == 2)
                    setData(movie, branch_name);
                seat.setStatus(2);
                seatList.add(seat);
            }
        }
        if(EventBus.getDefault().isRegistered(App.class))
            EventBus.getDefault().unregister(App.class);
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        openPayment(new BranchesListEvent());
    }

    @FXML
    void chooseSeats(ActionEvent event) {
        int wantedNum=numChoice.getValue();
        int size=movie.getScreeningTime().get(index).getMap().getSize();
        if((maxSeats * 1.2 < size && (taken + wantedNum > maxSeats)) ||
                (maxSeats * 0.8 < size && (taken + wantedNum > 0.8 * maxSeats)) ||
                (maxSeats * 0.8 >= size && (taken + wantedNum > 0.5 * size))){
            errorMsg.setVisible(true);
            return;
        }
        for(int j=0; j < size && wantedNum > 0; j++)
            if(movie.getScreeningTime().get(index).getMap().getSeats().get(j).getStatus()!=2){
                wantedNum--;
                seatChosen[j]=1;
                seats[j].setStyle("-fx-background-color: #8E8E8E");
            }
    }

    @Subscribe
    public void openPayment(BranchesListEvent branchesListEvent) throws IOException {
        Platform.runLater(() -> {
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        while (seatList.size()>0 && seat_found){
            Seat seat=seatList.get(seatList.size()-1);
            seatList.remove(seatList.size()-1);
            try {
                SimpleClient.getClient().sendToServer(seat);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }if(seat_found) {
                try {
                    EventBus.getDefault().unregister(this);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("creditcard.fxml"));
                    Parent root = loader.load();

                    CreditCardController itemController = loader.getController();
                    itemController.setData(time, seatChosen);

                    App.myStage.setScene(new Scene(root));
                    App.myStage.setMaximized(true);
                    App.myStage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private int taken=0;

    @Subscribe
    public void seatView(StringReceiver str){
        Platform.runLater(() -> {
                double maxSeats = Double.valueOf(str.getStr());
                System.out.println(maxSeats);
                Time time = movie.getScreeningTime().get(index);

                int size = time.getMap().getSize();
                Map map = time.getMap();
                this.maxSeats = maxSeats;

                int col = 0, row = 1, count = 0;
                seatChosen = new int[size];
                for (int i = 0; i < size; i++)
                    seatChosen[i] = 0;
                seats = new Button[size];

                for (int i = 0; i < size; i++) {
                    if (col == 8) {
                        col = 0;
                        row++;
                    }
                    seats[i] = new Button();
                    seats[i].setMinWidth(40);

                    seats[i].setText(map.getSeats().get(i).getName());
                    seats[i].setTextFill(Color.web("white"));

                    if (map.getSeats().get(i).getStatus() == 2) {
                        seats[i].setStyle("-fx-background-color: #0B0B0B");
                        taken++;
                    } else {
                        seats[i].setStyle("-fx-background-color: #800020");
                        int finalI = i;
                        if (maxSeats == -1) {
                            numChoice.setVisible(false);
                            seats[i].setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    if (seatChosen[finalI] == 0) {
                                        seatChosen[finalI] = 1;
                                        seats[finalI].setStyle("-fx-background-color: #8E8E8E");
                                    } else if (seatChosen[finalI] == 1) {
                                        seatChosen[finalI] = 0;
                                        seats[finalI].setStyle("-fx-background-color: #800020");
                                    }
                                }
                            });
                        } else {
                            numChoice.setVisible(true);
                            chooseSeats.setVisible(true);

                        }
                    }

                    grid.add(seats[i], col++, row);
                    GridPane.setMargin(seats[i], new Insets(0, 0, 10, 10));
                }
                if(EventBus.getDefault().isRegistered(this))
                    EventBus.getDefault().unregister(this);
        });
    }

}

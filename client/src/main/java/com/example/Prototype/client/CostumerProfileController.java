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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CostumerProfileController {

    @FXML
    private GridPane grid;

    @FXML
    private Label welcomeLabel;

    @FXML
    private Label infoLabel;

    private Costumer costumer;

    private int seatsNum=0;

    private Label[] labels;

    private Button[] buttons;

    public void setData(Costumer costumer){
        this.costumer=costumer;
    }

    @FXML
    void backOP(ActionEvent event) throws IOException {
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        SimpleClient.getClient().sendToServer("#InitilaizeVerCode" + String.valueOf(costumer.getId()));
    }

    @Subscribe
    public void backOP2(StringReceiver str) throws IOException {
        Platform.runLater(()-> {
            try {
                EventBus.getDefault().unregister(this);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
                Parent root = loader.load();

                SecondaryController itemController = loader.getController();
                itemController.setData();

                Scene newscene = new Scene(root, 600, 600);
                App.myStage.setScene(newscene);
                App.myStage.setMaximized(true);
                App.myStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void fillComplaint(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("fillcomplaint.fxml"));
        Parent root = loader.load();

        FillComplaintController itemController = loader.getController();
        itemController.setData(this.costumer);

        Scene newscene=new Scene(root,600,600);
        Stage stage=new Stage();
        stage.setScene(newscene);
        stage.show();
    }

    @FXML
    void returnSeat(ActionEvent event) {
        List<Ticket> tickets = new ArrayList<Ticket>();
        for(Ticket ticket:costumer.getTickets())
            if(ticket.getClass().equals(CinemaTicket.class))
                tickets.add(ticket);
        returOP(tickets, "cinema");
    }

    @FXML
    void returnURL(ActionEvent event) {
        List<Ticket> tickets = new ArrayList<Ticket>();
        for(Ticket ticket:costumer.getTickets())
            if(ticket.getClass().equals(HomeTicket.class))
                tickets.add(ticket);
        returOP(tickets, "home");
    }

    public void returOP(List<Ticket> tickets, String type){
        grid.getChildren().clear();
        int column=1,row=0;
        labels=new Label[tickets.size()];
        buttons=new Button[tickets.size()];
        int index=0;

        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

        for(Ticket ticket:tickets){
            if(!ticket.getClass().equals(HomeTicket.class) && ((CinemaTicket) ticket).getMultiple_tickets() != null)
                continue;
            labels[index]=new Label();
            labels[index].setWrapText(true);
            labels[index].setTextFill(Color.web("white"));
            if(type.equals("cinema")) {
                labels[index].setText(ticket.getDetails().getMovie().getName() + "  At  " + ticket.getDetails().getHall().getBranch().getName() +
                        "  hall:  " + ticket.getDetails().getHall().getName() + "  seat:  " + ticket.getSeat().getName());
            }else{
                labels[index].setText(ticket.getDetails().getMovie().getName() + " Starts At  " + ticket.getDetails().getBegTime());
            }
            grid.addRow(row, labels[0]);
            buttons[index]=new Button();
            buttons[index].setMinWidth(40);

            grid.addRow(row++, buttons[index]);

            buttons[index].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        SimpleClient.getClient().sendToServer("#ReturnTicket"+ticket.getId());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            row++;
        }
    }

    @FXML
    void showMultiTickets(ActionEvent event) {
        grid.getChildren().clear();
        int column=1,row=1;

        List<Movie> movieList = new ArrayList<Movie>();
        List<Integer> counter = new ArrayList<Integer>();
        List<Ticket> tikcets = costumer.getTickets();

        for(Ticket ticket:tikcets){
            boolean exist=false;
            if(ticket.getClass().equals(CinemaTicket.class) && ((CinemaTicket)ticket).getMultiple_tickets() != null){
                for(int i=0; i < movieList.size(); i++){
                    if(ticket.getDetails().getMovie() == movieList.get(i)) {
                        exist=true;
                        counter.add(i, counter.get(i) + 1);
                        i=movieList.size() + 1;
                    }
                }
                if(!exist){
                    counter.add(1);
                    movieList.add(ticket.getDetails().getMovie());
                }
            }
        }

        int i = 0;
        for(Movie movie:movieList){
            javafx.scene.image.Image image=new Image(getClass().getResourceAsStream(movie.getImgSrc()));
            ImageView imageView = new ImageView();
            imageView.setImage(image);
            imageView.setFitWidth(120);
            imageView.setFitHeight(120);
            grid.add(imageView, column++, row);

            Label label = new Label();
            label.setText(String.valueOf("Number of Tickets: " + counter.get(i)));
            label.setTextFill(Color.web("white"));
            i++;
            grid.add(label, column--, row++);
        }
    }

    @Subscribe
    public void updatedCostumer(Costumer costumer){
            Platform.runLater(()-> {
                    this.costumer = costumer;
            });
    }
}

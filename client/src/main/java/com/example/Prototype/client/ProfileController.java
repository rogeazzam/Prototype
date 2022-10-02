package com.example.Prototype.client;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class ProfileController {


    @FXML
    private Label welcomeUser;

    @FXML
    private GridPane processGrid;

    @FXML
    private Button addBtn;

    @FXML
    private Button reportBtn;

    @FXML
    private Button showComplaintsBtn;

    @FXML
    private Button priceChange;

    private Person user;

    private String user_type;

    int column=0,row=2;

    public void setData(Person person){
        this.user=person;
        this.user_type=user.getType();
        if(user_type.equals("ContentManager")) {
            addBtn.setVisible(true);
        }
        if(user_type.equals("Employee")) {
            showComplaintsBtn.setVisible(true);
        }else
            reportBtn.setVisible(true);

        if(user_type.equals("NetworkManager"))
            priceChange.setVisible(true);
    }

    @Subscribe
    public void display(MovieListEvent movies) throws IOException {
        //addBtn.setVisible(false);
        //processGrid.getChildren().clear();
        column=0;
        row=2;
        Platform.runLater(()-> {
            try {
                processGrid.getChildren().clear();
            List<Movie> movies1 = movies.getMovies().getMovies();
            for (Movie movie : movies1) {
                if (column == 4) {
                    row++;
                    column = 0;
                }
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(this.getClass().getResource("movie.fxml"));
                AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();

                MovieController itemController = fxmlLoader.getController();
                itemController.setData(movie);
                itemController.setFxmlFile("showmovie");
                if(!user.getClass().equals(ContentManager.class))
                    itemController.setFxmlFile("/");

                processGrid.add(anchorPane, column, row);
                //set grid width
                processGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
                processGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                processGrid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                processGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
                processGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                processGrid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(0, 0, 10, 0));
                column++;
            }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void reportsEvent(javafx.event.ActionEvent actionEvent) throws IOException {
        EventBus.getDefault().register(this);
        SimpleClient.getClient().sendToServer("#getReports");
    }

    @FXML
    public void moviesEvent(javafx.event.ActionEvent actionEvent) throws IOException {
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

        SimpleClient.getClient().sendToServer("#showMovies");
    }

    @FXML
    void addMovieOp(ActionEvent event) throws IOException {
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("addmovie.fxml"));
        Parent root = loader.load();

        AddMovieController itemController = loader.getController();
        itemController.setData();

        Scene newscene=new Scene(root,600,600);
        Stage stage=new Stage();
        stage.setScene(newscene);
        stage.show();
    }

    @FXML
    void priceChangeEvent(ActionEvent event) throws IOException {
        EventBus.getDefault().register(this);
        SimpleClient.getClient().sendToServer("#getMoviesToUpdate");
    }

    @FXML
    void logOutOp(ActionEvent event) throws IOException {
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        SimpleClient.getClient().sendToServer("#LogOut" + user.getId());
    }

    @FXML
    void showComplaintsOP(ActionEvent event) throws IOException {
        EventBus.getDefault().register(this);
        SimpleClient.getClient().sendToServer("#getComplaints");
    }

    @FXML
    void updatePurplePass(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("purplepass.fxml"));
            Parent root = null;
            root = loader.load();

            PurplePassController itemController = loader.getController();
            itemController.setData();

            Scene scene = new Scene(root);

            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    @Subscribe
    public void LogOut(StringReceiver str) throws IOException {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(this);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
                Parent root = null;
                root = loader.load();

                SecondaryController itemController = loader.getController();
                itemController.setData();

                Scene scene = new Scene(root);

                App.myStage.setScene(scene);
                App.myStage.setMaximized(true);
                App.myStage.show();
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    @Subscribe
    public void showPriceChange(List<Time> times){
        Platform.runLater(() -> {
                EventBus.getDefault().unregister(this);
                processGrid.getChildren().clear();
                int row = 4, column = 1;

                int size = times.size();
                Label[] labels = new Label[size];
                Button[] buttons1 = new Button[size];
                Button[] buttons2 = new Button[size];
                for (int i = 0; i < size; i++) {
                    Time time = times.get(i);
                    System.out.println("fks,1");
                    labels[i] = new Label();
                    labels[i].setWrapText(true);
                    String f=String.valueOf(time.getDay());
                    /*labels[i].setText(String.valueOf(time.getDay()) + "/" + String.valueOf(time.getMonth()) +
                            "/" + String.valueOf(time.getYear()) + "  " + time.getBegTime());*/
                    labels[i].setText("fsd");
                    buttons1[i] = new Button();
                    buttons1[i].setText("Approve");
                    buttons2[i] = new Button();
                    buttons2[i].setText("Disapprove");
                    int finalI = i;
                    buttons1[i].setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                SimpleClient.getClient().sendToServer("#UpdateTicketsPrice" + "%%" +
                                        String.valueOf(times.get(finalI).getId()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    buttons2[i].setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            try {
                                SimpleClient.getClient().sendToServer("#DisApproveUpdate" +
                                        String.valueOf(times.get(finalI).getId()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                    processGrid.add(labels[i], column++, row);
                    processGrid.add(buttons1[i], column++, row);
                    processGrid.add(buttons2[i], column, row++);
                    column = 1;
                }
        });

    }

    @Subscribe
    public void openReports(ReportList temp) throws IOException {
        Platform.runLater(() -> {
            try {
                List<Report> stats = temp.getReports();
                EventBus.getDefault().unregister(this);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("report.fxml"));
                Parent root = null;
                root = loader.load();

                ReportController itemController = loader.getController();
                itemController.setData(this.user, stats);

                Scene scene = new Scene(root);

                App.myStage.setScene(scene);
                App.myStage.setMaximized(true);
                App.sceneStack.push(reportBtn.getScene());
                App.myStage.show();
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }

    @Subscribe
    public void openComplaints(ComplaintReportList complaintReportList){
        Platform.runLater(() -> {
            try {
                List<ComplaintReport> complaints = complaintReportList.getComplaints();
                EventBus.getDefault().unregister(this);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("showcomplaints.fxml"));
                Parent root = null;
                root = loader.load();

                ShowComplaintsController itemController = loader.getController();
                itemController.setData(complaints);

                Scene scene = new Scene(root);

                App.myStage.setScene(scene);
                App.myStage.setMaximized(true);
                App.sceneStack.push(reportBtn.getScene());
                App.myStage.show();
            }catch (IOException e){
                e.printStackTrace();
            }
        });
    }
}

package com.example.Prototype.client;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;

public class ProfileController {


    @FXML
    private Label welcomeUser;

    @FXML
    private GridPane processGrid;

    @FXML
    private Button btn;

    int column=0,row=2;

    public void setData(Person person){

    }

    @Subscribe
    public void display(MovieListEvent movies) throws IOException {
        btn.setVisible(false);
        Platform.runLater(()-> {
            try {
            List<Movie> movies1 = movies.getMovies().getMovies();
            for (Movie movie : movies1) {
                if (column == 4) {
                    row++;
                    column = 0;
                }
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(this.getClass().getResource("movie.fxml"));
                //AnchorPane anchorPane = null;
                AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();

                MovieController itemController = fxmlLoader.getController();
                itemController.setData(movie);
                itemController.setFxmlFile("showmoviecostumer");

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
    public void reportsEvent(javafx.event.ActionEvent actionEvent) {
    }

    @FXML
    public void moviesEvent(javafx.event.ActionEvent actionEvent) throws IOException {
        EventBus.getDefault().register(this);

        SimpleClient.getClient().sendToServer("#showMovies");
    }
}

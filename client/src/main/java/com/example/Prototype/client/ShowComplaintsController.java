package com.example.Prototype.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ShowComplaintsController {

    @FXML
    private GridPane grid;

    private List<ComplaintReport> complaints=new ArrayList<ComplaintReport>();

    public void setData(List<ComplaintReport> complaints) throws IOException {
        this.complaints = complaints;

        int column=0;
        int row=1;
        for(ComplaintReport complaint:complaints) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(this.getClass().getResource("complaint.fxml"));
            AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();

            ComplaintController itemController = fxmlLoader.getController();
            itemController.setData(complaint);

            grid.add(anchorPane, column, row);
            //set grid width
            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxWidth(Region.USE_PREF_SIZE);

            //set grid height
            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setMaxHeight(Region.USE_PREF_SIZE);

            GridPane.setMargin(anchorPane, new Insets(0, 0, 10, 0));
            row++;
        }
    }

    @FXML
    void backOP(ActionEvent event) {
        App.myStage.setScene(App.sceneStack.pop());
        App.myStage.setMaximized(true);
        App.myStage.show();
    }
}

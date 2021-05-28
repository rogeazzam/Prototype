package com.example.Prototype.client;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class SecondaryController implements Initializable{

    @FXML
    private VBox adBox;

    @FXML
    private Label movieNameLabel;

    @FXML
    private ImageView ClassicImg;

    @FXML // fx:id="scroll"
    private ScrollPane scroll; // Value injected by FXMLLoader

    @FXML // fx:id="grid"
    private GridPane grid; // Value injected by FXMLLoader
    
    @FXML
    private AnchorPane anchor1;
    
    @FXML
    private TextField SearchText;
    
    BranchesList branch;

    int column=0,row=1;

    int entered=0;


    @FXML
    void BranchOpen(ActionEvent event) throws IOException {
        SimpleClient.getClient().sendToServer("#showMovies");
        /*FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/Prototype/client/showmovie.fxml"));
        Parent root = loader.load();

        Time time1=new Time(22,1,52,"52","4");
        ShowMovieController itemController = loader.getController();
        itemController.setData(new Movie("hhhh","ssss","kkk","images/2.jpg","sf;lj gijf",time1));

        //Stage stage = new Stage();
        Scene scene=new Scene(root,600,600);
        App.myStage.setScene(scene);
        App.myStage.setFullScreen(true);
        App.myStage.show();*/
    }

    @FXML
    void SearchBtn(ActionEvent event) throws IOException {
    	grid.getChildren().clear();
    	List<Branch> branches1=new ArrayList<Branch>();
    	branches1=branch.getBranches();
        BranchesList branches2=new BranchesList();
    	for(int i=0;i<branches1.size();i++) {
            if (branches1.get(i).getCity().toLowerCase().contains(SearchText.getText().toLowerCase()))
                branches2.setBranch(branches1.get(i));
        }
    		display(branches2);
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	@Subscribe
	void display(BranchesList branchesList) throws IOException {
        EventBus.getDefault().register(this);
        entered++;
        if(entered==0)
            this.branch=branchesList;
        List<Branch> branches=branchesList.getBranches();
        for(Branch branch:branches) {
            if (column == 4) {
                row++;
                column = 0;
            }
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(this.getClass().getResource("com/example/Prototype/client/branches.fxml"));
            AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();

            BranchesController itemController = fxmlLoader.getController();
            itemController.setData(branch);

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
        }
	}

}

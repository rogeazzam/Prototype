package com.example.Prototype.client;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.example.CinemaPrototype.Classes.Branch;
import com.example.CinemaPrototype.Classes.Movie;
import com.example.CinemaPrototype.Classes.MovieList;
import com.example.CinemaPrototype.Classes.Time;

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
    
    Branch[] branch;
    
    @FXML
    void BranchOpen(ActionEvent event) {
		try {
			SimpleClient.getClient().sendToServer("#showBranches");
			int i=0;
			while(i<2) {
				display(branch[i],0,i+1);
				i++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @FXML
    void SearchBtn(ActionEvent event) throws IOException {
    	grid.getChildren().clear();
    	for(int i=0;i<branch.length;i++) {
    		if(branch[i].getCity().toLowerCase().contains(SearchText.getText().toLowerCase())) {
    			display(branch[i],0,i+1);
    		}
    	}
    }

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
	
	void display(Branch branch,int column,int row) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(this.getClass().getResource("branches.fxml"));
		AnchorPane anchorPane = (AnchorPane)fxmlLoader.load();
	
		BranchesController itemController =fxmlLoader.getController();
		itemController.setData(branch);
		
		grid.add(anchorPane,column,row);
        //set grid width
        grid.setMinWidth(Region.USE_COMPUTED_SIZE);
        grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
        grid.setMaxWidth(Region.USE_PREF_SIZE);

        //set grid height
        grid.setMinHeight(Region.USE_COMPUTED_SIZE);
        grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
        grid.setMaxHeight(Region.USE_PREF_SIZE);

        GridPane.setMargin(anchorPane, new Insets(0,0,10,0));
	}

}

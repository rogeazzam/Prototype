package com.example.Prototype.client;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class BranchesController {

	private Branch branch;
	
    @FXML
    private Label nameLabel;

    @FXML
    private ImageView img;

    @FXML
    private GridPane grid;

    @FXML
    void showDetails(ActionEvent event) {
    	
    }

    @FXML
    void showLocation(ActionEvent event) {
    	Label text1=new Label();
    	text1.setText("City: "+branch.getCity()+"\n"+"Address: "+branch.getStreetAddress());
    	grid.add(text1,0,0);
    }

    @FXML
    void showMovies(ActionEvent event) throws IOException {
        EventBus.getDefault().register(this);
        SimpleClient.getClient().sendToServer("#showBranchMovies"+String.valueOf(branch.getId()));
    }

    @Subscribe
    public void showBMovies(MovieListEvent event) throws IOException {
        Platform.runLater(()->{
            MovieList movies=event.getMovies();
            try {
                EventBus.getDefault().unregister(this);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("movielist.fxml"));
                Parent root = loader.load();

                MovieListController itemController = loader.getController();
                itemController.setData(movies, "purchasingticket"+branch.getName());

                Scene scene=new Scene(root);
                App.myStage.setScene(scene);
                SecondaryController.saveScene=scene;
                App.myStage.setFullScreen(true);
                App.myStage.show();
            }catch (IOException e) {
            e.printStackTrace();
        }
    });
    }
    
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public void setData(Branch branch) {
    	this.branch=branch;
    	Image image=new Image(getClass().getResourceAsStream(branch.getImgSrc()));
    	img.setImage(image);
    	nameLabel.setText(branch.getName());
    }

}

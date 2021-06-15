package com.example.Prototype.client;

import java.awt.event.ActionEvent;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class MovieController {


    @FXML
    private Label MovieName;

    @FXML
    private Button MyBtn;

    @FXML
    private ImageView btnImage;
    
    @FXML
    private ScrollPane scroll;
    
    private Movie movie;

    private String type;

    private String fxmlFile;

    public MovieController(){
        this.type= "costumer";
        this.fxmlFile="showmovie";
    }

    public void setFxmlFile(String fxmlFile){
        this.fxmlFile=fxmlFile;
    }

    @FXML
    void ImgHit(MouseEvent event) throws IOException {
        display();
    }

    private void display() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile+".fxml"));
        Parent root = loader.load();

        if(fxmlFile=="showmovie") {
            ShowMovieController itemController = loader.getController();
            itemController.setData(movie);
        }else if(fxmlFile=="showmoviecostumer"){
            ShowMovieCostumerController itemController = loader.getController();
            itemController.setData(movie);
        }

        Scene scene=new Scene(root,600,600);
        App.myStage.setScene(scene);
        App.myStage.setFullScreen(true);
        App.myStage.show();
    }

	public void setData(Movie movie1) {
		this.movie=movie1;
		MovieName.setText(movie.getName());
		Image image=new Image(getClass().getResourceAsStream(movie.getImgSrc()));
		btnImage.setImage(image);
	}

	@FXML
    public void showIt(javafx.event.ActionEvent actionEvent) throws IOException {
        display();
    }

    @FXML
    public void editIt(javafx.event.ActionEvent actionEvent) throws IOException {

    }
}

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

    @FXML
    void ImgHit(MouseEvent event) throws IOException {
        display();
    }

    private void display() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("showmovie.fxml"));
        Parent root = loader.load();

        ShowMovieController itemController = loader.getController();
        itemController.setData(movie);

        //Stage stage = new Stage();
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
}

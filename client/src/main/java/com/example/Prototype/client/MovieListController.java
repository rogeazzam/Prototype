package com.example.Prototype.client;

import java.io.IOException;
import java.util.List;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

public class MovieListController{
	
    @FXML
    private Label titleLabel;

    @FXML
    private GridPane grid;
    
	private MovieList movies;
	
	int column=0,row=1;

	public void setData(MovieList movies) throws IOException {
		this.movies=movies;
		List<Movie> list=movies.getMovies();
		for(Movie movie : list) {
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	fxmlLoader.setLocation(this.getClass().getResource("com/example/Prototype/client/movie.fxml"));
		AnchorPane anchorPane;
		anchorPane = (AnchorPane)fxmlLoader.load();

		MovieController itemController =fxmlLoader.getController();
		itemController.setData(movie);
		
		grid.add(anchorPane,column++,row);
		
		if(column==3) {
			column=0;
			row++;
		}
		
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
	
    @FXML
    void BackButton(ActionEvent event) {
		App.myStage.setScene(App.sceneStack.pop());
		App.myStage.setFullScreen(true);
		App.myStage.show();
    }
    
    @Subscribe
    public void displayMovies(MovieList movies) throws IOException {
		List<Movie> list=movies.getMovies();
		for(Movie movie : list) {
    	FXMLLoader fxmlLoader = new FXMLLoader();
    	fxmlLoader.setLocation(this.getClass().getResource("com/example/Prototype/client/movie.fxml"));
		AnchorPane anchorPane;
		anchorPane = (AnchorPane)fxmlLoader.load();

		MovieController itemController =fxmlLoader.getController();
		itemController.setData(movie);
		
		grid.add(anchorPane,column++,row);
		
		if(column==3) {
			column=0;
			row++;
		}
		
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
    
}

package com.example.Prototype.client;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
		display(list);
	}

	@FXML
	void sortByDate(ActionEvent event) throws IOException {
		List<Movie> list=movies.getMovies();
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
		System.out.println(timeStamp);
		String[] parts = timeStamp.split(".");
		List<Movie> sorted = new ArrayList<>();
		int min=list.get(0).getScreeningTime().getYear();
		int max=min;
		for(Movie movie: list){
			int time=movie.getScreeningTime().getYear();
			if(time<min)
				min=time;
			if(time>max)
				max=time;
		}

		for(int i=min;i <= max; i++){
			for(int m=0;m <= 12; m++) {
				for(int d=0;d <= 31; d++) {
					for (int h = 0; h <= 23; h++) {
						for(int mi=0;mi <= 59; mi++) {
							for (Movie movie : list) {
								int year=movie.getScreeningTime().getYear();
								int month=movie.getScreeningTime().getMonth();
								int day=movie.getScreeningTime().getDay();
								String[] time = movie.getScreeningTime().getBegTime().split(":");
								int hour=Integer.parseInt(time[0]);
								int minute=Integer.parseInt(time[1]);
								if (minute == mi && hour==h && day==d && month==m && year==i)
									sorted.add(movie);
							}
						}
					}
				}
			}
		}
		/*Collections.copy(list,sorted);
		sorted.clear();

		int counter=min;
		for(int i=0;i <= 12; i++){
			for(Movie movie: list){
				int time=movie.getScreeningTime().getMonth();
				if(time==i)
					sorted.add(movie);
			}
		}
		Collections.copy(list,sorted);
		sorted.clear();

		for(int i=0;i <= 31; i++){
			for(Movie movie: list){
				int time=movie.getScreeningTime().getDay();
				if(time==i)
					sorted.add(movie);
			}
		}
		Collections.copy(list,sorted);
		sorted.clear();

		for(int i=0;i <= 24; i++){
			for(Movie movie: list){
				String[] time=movie.getScreeningTime().getBegTime().split(":");
				int hour=Integer.parseInt(time[0]);
				if(hour==i)
					sorted.add(movie);
			}
		}

		Collections.copy(list,sorted);
		sorted.clear();

		for(int i=0;i <= 60; i++){
			for(Movie movie: list){
				String[] time=movie.getScreeningTime().getBegTime().split(":");
				int minute=Integer.parseInt(time[1]);
				if(minute==i)
					sorted.add(movie);
			}
		}*/

		display(sorted);
	}


	@FXML
	void showHomeWatch(ActionEvent event) throws IOException {
		//EventBus.getDefault().unregister(App.class);
		EventBus.getDefault().register(this);
		SimpleClient.getClient().sendToServer("#HomeWatchList");
	}

	@Subscribe
	public void display(List<Movie> list) throws IOException {
		grid.getChildren().clear();
		column=0;
		row=1;
		for(Movie movie : list) {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(this.getClass().getResource("movie.fxml"));
			AnchorPane anchorPane;
			anchorPane = (AnchorPane)fxmlLoader.load();

			MovieController itemController =fxmlLoader.getController();
			itemController.setData(movie);
			itemController.setFxmlFile("showmoviecostumer");

			grid.add(anchorPane,column++,row);

			if(column==4) {
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

			GridPane.setMargin(anchorPane, new Insets(0,0,10,30));
		}
	}
}

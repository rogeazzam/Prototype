package com.example.Prototype.client;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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

public class MovieListController implements Initializable {
	
    @FXML
    private Label titleLabel;

    @FXML
    private GridPane grid;

	@FXML
	private Button homeMovies;
    
	private MovieList movies;

	private String type;
	
	int column=0,row=1;

	public void setData(MovieList movies) throws IOException {
		this.movies=movies;
		List<Movie> list=movies.getMovies();
		this.type="showmoviecostumer";
		display(list, "showmoviecostumer");
	}

	public void setData(MovieList movies,String type) throws IOException {
		this.movies=movies;
		List<Movie> list=movies.getMovies();
		if(type=="purchasingticket")
			homeMovies.setVisible(false);
		this.type="purchasingticket";
		display(list,type);
	}


	@FXML
	void sortByDate(ActionEvent event) throws IOException {
		List<Movie> list=movies.getMovies();
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new java.util.Date());
		System.out.println(timeStamp);
		String[] parts = timeStamp.split(".");
		List<Movie> sorted = new ArrayList<Movie>();

		/*int min=list.get(0).getScreeningTime().get(0).getYear();
		int max=min;
		for(Movie movie: list){
			int time=movie.getScreeningTime().get(0).getYear();
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
								int year=movie.getScreeningTime().get(0).getYear();
								int month=movie.getScreeningTime().get(0).getMonth();
								int day=movie.getScreeningTime().get(0).getDay();
								String[] time = movie.getScreeningTime().get(0).getBegTime().split(":");
								int hour=Integer.parseInt(time[0]);
								int minute=Integer.parseInt(time[1]);
								if (minute == mi && hour==h && day==d && month==m && year==i)
									sorted.add(movie);
							}
						}
					}
				}
			}
		}*/

		sorted.addAll(list);

		for(int i=0; i < sorted.size()-1; i++){
			for(int j=0; j< sorted.size()-i-1; j++){
				if(sorted.get(j).getScreeningTime().get(0).greater(sorted.get(j+1).getScreeningTime().get(0))) {
					Collections.swap(sorted, j, j+1);
				}
			}
		}

		display(sorted,type);
	}


	@FXML
	void showHomeWatch(ActionEvent event) throws IOException {
		EventBus.getDefault().unregister(App.class);
		EventBus.getDefault().register(this);
		SimpleClient.getClient().sendToServer("#HomeWatchList");
	}

	@Subscribe
	public void homeWatchEvent(MovieListEvent movieListEvent) throws IOException {
		MovieList movieList=movieListEvent.getMovies();
		List<Movie> movies=movieList.getMovies();
		display(movies, "showmoviecostumer");
		EventBus.getDefault().unregister(this);
		EventBus.getDefault().register(App.class);
	}

	@Subscribe
	public void display(List<Movie> list, String type) throws IOException {
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
			itemController.setFxmlFile(type);

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

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}

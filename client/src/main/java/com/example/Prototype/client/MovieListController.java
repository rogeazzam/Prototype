package com.example.Prototype.client;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import javafx.application.Platform;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;

public class MovieListController implements Initializable {
	
    @FXML
    private Label titleLabel;

    @FXML
    private GridPane grid;

	@FXML
	private Button sortBtn;

	@FXML
	private Button purchaseBtn;

	@FXML
	private Label msgLabel;
    
	private MovieList movies;

	private String type;
	
	int column=0,row=1;

	@FXML
	private TextField SearchText;

	@FXML
	private AnchorPane multiAnchor;

	@FXML
	private ChoiceBox<String> timeChoice;

	@FXML
	private ChoiceBox<Integer> seatsChoice;

	private List<Movie> orderedMovies=new ArrayList<Movie>();

	private String toSend="";

	private int size=0;

	private boolean timeChosen=false;

	private Movie movie=null;

	private Time time;

	private List<Seat> seats=new ArrayList<Seat>();

	private double purplepassBeg=-1;

	private double purplepassEnd=-1;

	/*this function receives object of class MovieController (not null),
	 then takes the movie in it and add's it to the orderMovies list.
	 */
	public void addToOrderMovies(MovieController movieController){
		if(this.size>=20)
			return;
		Objects.requireNonNull(movieController);
		movie = movieController.getMovie();
		System.out.println("daskm");

		timeChoice.getItems().clear();
		for(int i = 0; i < movie.getScreeningTime().size(); i++) {
			Time time = movie.getScreeningTime().get(i);
			String str = String.valueOf(time.getDay()) + "/" + String.valueOf(time.getMonth()) + "/"
							+ String.valueOf(time.getYear()) + "  " + time.getBegTime();
			timeChoice.getItems().add(str);
		}
		timeChoice.setValue(timeChoice.getItems().get(0));
		timeChosen=true;

	}

	@FXML
	void backOP(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("secondary.fxml"));
		Parent root = loader.load();

		SecondaryController itemController = loader.getController();
		itemController.setData();

		Scene newscene=new Scene(root,600,600);
		App.myStage.setScene(newscene);
		App.myStage.setMaximized(true);
		App.myStage.show();
	}

	@FXML
	void seatNums(MouseEvent event) {
		if(!timeChosen)
			return;
		seatsChoice.getItems().clear();
		int index=0;
		for(int i = 0; i < timeChoice.getItems().size(); i++) {
			if (timeChoice.getValue().equals(timeChoice.getItems().get(i))) {
				index = i;
				break;
			}
		}
		time = movie.getScreeningTime().get(index);
		List<Seat> seats = time.getMap().getSeats();

		int count=0;
		for(Seat seat:seats){
			if(seat.getStatus() == 0)
				count++;
			if (count == 20)
				break;
		}

		for(int i = 1; i <= count - this.size; i++)
			seatsChoice.getItems().add(i);
	}

	@FXML
	void addOP(ActionEvent event) {
		List<Seat> seats = time.getMap().getSeats();
		int count=seatsChoice.getValue();
		int count2=0;
		int index=0;

		for(Seat seat:seats) {
			if(seat.getStatus() == 0) {
				if(--count < 0)
					break;
				count2++;
				seat.setStatus(2);
				this.seats.add(seat);
				toSend += String.valueOf(index) + ",";
			}
			index++;
		}
		this.size += count2;

		if(seatsChoice.getValue() - count != 0)
			toSend += String.valueOf(time.getId()) + "ss";
	}

	@FXML
	void SearchBtn(ActionEvent event) throws IOException {
		grid.getChildren().clear();
		List<Movie> movies1=new ArrayList<Movie>();
		movies1=movies.getMovies();
		List<Movie> movieList=new ArrayList<Movie>();
		for(int i=0; i < movies1.size(); i++) {
			if (movies1.get(i).getName().toLowerCase().contains(SearchText.getText().toLowerCase()))
				movieList.add(movies1.get(i));
		}

		display(movieList, this.type);
	}

	public void setData(MovieList movies) throws IOException {
		this.movies=movies;
		List<Movie> list=movies.getMovies();
		this.type="showmoviecostumer";
		display(list, "showmoviecostumer");
	}

	public void setData(MovieList movies,String type) throws IOException {
		if(type.equals("movies to come")){
			sortBtn.setVisible(false);
			setData(movies);
			return;
		}else if(type.equals("#multiTickets")){
			purchaseBtn.setVisible(true);
			multiAnchor.setVisible(true);
			Image image=new Image(getClass().getResourceAsStream("images/seats.jpg"));
			grid.setBackground(new Background(new BackgroundImage(image,
					BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
		}
		this.movies=movies;
		List<Movie> list=movies.getMovies();
		this.type=type;
		display(list,type);
	}

	@FXML
	void purchaseOP(ActionEvent event) {
		purchaseSeats(null);
	}

	@Subscribe
	public void purchaseSeats(BranchesListEvent branchesListEvent){
		Platform.runLater(() -> {
			if(!EventBus.getDefault().isRegistered(this))
				EventBus.getDefault().register(this);
			while (seats.size() > 0){
				Seat seat=seats.get(0);
				seats.remove(0);
				try {
					SimpleClient.getClient().sendToServer(seat);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
				try {
					EventBus.getDefault().unregister(this);

					FXMLLoader loader = new FXMLLoader(getClass().getResource("creditcard.fxml"));
					Parent root = loader.load();

					CreditCardController itemController = loader.getController();
					itemController.setData(toSend.substring(0, toSend.length()-2));

					App.myStage.setScene(new Scene(root));
					App.myStage.setMaximized(true);
					App.myStage.show();
				} catch (IOException e) {
					e.printStackTrace();
				}
		});
	}

	@FXML
	void sortByDate(ActionEvent event) throws IOException {
		List<Movie> list=movies.getMovies();
		List<Movie> sorted = new ArrayList<Movie>();

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

	@Subscribe
	public void display(List<Movie> list, String type) throws IOException {
		grid.getChildren().clear();
		column=0;
		row=1;
		App.sceneStack.push(sortBtn.getScene());
		for(Movie movie : list) {

			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation(this.getClass().getResource("movie.fxml"));
			AnchorPane anchorPane;
			anchorPane = (AnchorPane)fxmlLoader.load();

			MovieController itemController =fxmlLoader.getController();
			itemController.setData(movie);
			itemController.setFxmlFile(type);

			if(this.type.equals("#multiTickets")) {
				itemController.setMovieListController(this);
			}

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

	@Subscribe
	public void checkPurplePass(StringReceiver str){

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}
}

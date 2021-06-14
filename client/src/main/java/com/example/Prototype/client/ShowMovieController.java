package com.example.Prototype.client;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.time.Month;

public class ShowMovieController {

    @FXML
    private ImageView movieImg;

    @FXML
    private Label movieText;

    @FXML
    private Label movieName;
    
    private Movie movie;

	@FXML
	private ChoiceBox<Integer> DayOp;
	@FXML
	private ChoiceBox<Integer> MonthOp;
	@FXML
	private ChoiceBox<Integer> YearOp;
	@FXML
	private ChoiceBox<String> BegMinute;
	@FXML
	private ChoiceBox<String> BegHour;
	@FXML
	private ChoiceBox<String> endHour;
	@FXML
	private ChoiceBox<String> endMinute;
	@FXML
	private Label screeningTimeLabel;
	@FXML
	private Label errorMsg;
    
    public void setData(Movie movie) {
    	setChoices();
    	this.movie=movie;
		movieName.setText(movie.getName());
		Image image=new Image(getClass().getResourceAsStream(movie.getImgSrc()));
		movieImg.setImage(image);
		movieText.setText(movie.getText());
		screeningTimeLabel.setText("Date of screening: " + movie.getScreeningTime().getDay()
				+ "/" + movie.getScreeningTime().getMonth()+ "/" + movie.getScreeningTime().getYear()+ "   "
				+ "At " + movie.getScreeningTime().getBegTime());
    	
    }

    private void setChoices(){
		ObservableList list=FXCollections.observableArrayList();
		for(Integer i=1;i<=12;i++)
			list.add(i);
		MonthOp.getItems().addAll(list);
		list.clear();
		for(Integer i=2010;i<=2025;i++)
			list.add(i);
		YearOp.getItems().addAll(list);
		list.clear();
		for(Integer i=1;i<=31;i++)
			list.add(i);
		DayOp.getItems().addAll(list);
		list.clear();
		for(Integer i=0;i<24;i++) {
			String str;
			if (i < 10)
				str = "0" + String.valueOf(i);
			else
				str=String.valueOf(i);
			list.add(str);
		}
		BegHour.getItems().addAll(list);
		endHour.getItems().addAll(list);
		list.clear();
		for(Integer i=0;i<60;i++) {
			String str;
			if (i < 10)
				str = "0" + String.valueOf(i);
			else
				str=String.valueOf(i);
			list.add(str);
		}
		BegMinute.getItems().addAll(list);
		endMinute.getItems().addAll(list);
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	@FXML
	public void UpdateTime(javafx.event.ActionEvent actionEvent) throws IOException {
    	errorMsg.setText("");
    	if(!validDate()) {
			errorMsg.setText("Entered date is invalid");
		}

    	else {
    		Time time1=new Time((int) (DayOp.getValue()), (int) MonthOp.getValue(), (int) YearOp.getValue(),
					String.valueOf(BegHour.getValue()) +":"+  String.valueOf(BegMinute.getValue()),
					String.valueOf(endHour.getValue()) +":"+ String.valueOf(endMinute.getValue()));
			movie.setScreeningTime(time1);
			screeningTimeLabel.setText("Date of screening: " + String.valueOf(DayOp.getValue())
					+ "/" + String.valueOf(MonthOp.getValue()) + "/" + String.valueOf(YearOp.getValue()) + "   "
					+ "At " + BegHour.getValue() + ":" + BegMinute.getValue());
			SimpleClient.getClient().sendToServer(time1);
			SimpleClient.getClient().sendToServer(movie);
		}
	}

	private boolean validDate(){
		switch (MonthOp.getValue()){
			case 4:case 6: case 9: case 11:
				if(DayOp.getValue()==31)
					return false;
				break;
			case 2:
				if(DayOp.getValue()>=30)
					return false;
				else if((YearOp.getValue()%4!=0 || YearOp.getValue()%100==0) && DayOp.getValue()>28)
					return false;
				break;
		}
		return true;
	}

	@FXML
	public void showMovies(javafx.event.ActionEvent actionEvent) throws IOException {
		SimpleClient.getClient().sendToServer("#showMovies");
	}
}
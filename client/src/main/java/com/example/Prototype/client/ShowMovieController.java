package com.example.Prototype.client;


import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.hibernate.SessionFactory;

import java.io.IOException;
import java.time.Month;
import java.util.Collections;

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

	@FXML
	private GridPane btnsGrid;

	@FXML
	private ChoiceBox<Integer> updateChoice;
    
    public void setData(Movie movie) {
    	int row=1;
    	setChoices();
    	this.movie=movie;
		movieName.setText(movie.getName());
		Image image=new Image(getClass().getResourceAsStream(movie.getImgSrc()));
		movieImg.setImage(image);
		movieText.setText(movie.getText());

		int size=movie.getScreeningTime().size();

		System.out.println(size);
		for(int i = 0; i < size ; i++){
			updateChoice.getItems().add(i+1);
		}
		updateChoice.setValue(1);

		screeningTimeLabel.setWrapText(true);
		for (int i=0; i < size; i++) {
			screeningTimeLabel.setText(screeningTimeLabel.getText() + "Date of screening: " + movie.getScreeningTime().get(i).getDay()
					+ "/" + movie.getScreeningTime().get(i).getMonth() + "/" + movie.getScreeningTime().get(i).getYear() + "   "
					+ "At " + movie.getScreeningTime().get(i).getBegTime() + "\n");
		}
    	
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
    	UpdateTimefunc(updateChoice.getValue()-1);
	}

	public void UpdateTimefunc(int index) throws IOException {
		errorMsg.setText("");
		if(!validDate()) {
			errorMsg.setText("Entered date is invalid");
		}

		else {
			/*Time time1=new Time((int) (DayOp.getValue()), (int) MonthOp.getValue(), (int) YearOp.getValue(),
					String.valueOf(BegHour.getValue()) +":"+  String.valueOf(BegMinute.getValue()),
					String.valueOf(endHour.getValue()) +":"+ String.valueOf(endMinute.getValue()));
			movie.setScreeningTime(time1);*/

			screeningTimeLabel.setText(screeningTimeLabel.getText().replaceAll(
					"Date of screening: " +String.valueOf(movie.getScreeningTime().get(index).getDay())
							+ "/" + String.valueOf(movie.getScreeningTime().get(index).getMonth())
							+ "/" + String.valueOf(movie.getScreeningTime().get(index).getYear())+ "   "
							+ "At " + movie.getScreeningTime().get(index).getBegTime(), "Date of screening: " + String.valueOf(DayOp.getValue())
							+ "/" + String.valueOf(MonthOp.getValue()) + "/" + String.valueOf(YearOp.getValue()) + "   "
							+ "At " + BegHour.getValue() + ":" + BegMinute.getValue()));

			movie.getScreeningTime().get(index).setDay(Integer.parseInt(String.valueOf(DayOp.getValue())));
			movie.getScreeningTime().get(index).setMonth(Integer.parseInt(String.valueOf(MonthOp.getValue())));
			movie.getScreeningTime().get(index).setYear(Integer.parseInt(String.valueOf(YearOp.getValue())));
			movie.getScreeningTime().get(index).setBegTime(String.valueOf(BegHour.getValue()) + ":" + String.valueOf(BegMinute.getValue()));
			movie.getScreeningTime().get(index).setEndTime(String.valueOf(endHour.getValue()) +":"+ String.valueOf(endMinute.getValue()));

			for(int i=index; i > 0; i--){
				if(!movie.getScreeningTime().get(0).greater(movie.getScreeningTime().get(i-1)))
					Collections.swap(movie.getScreeningTime(),i,i-1);
				else
					break;
			}

			/*String id=String.valueOf(movie.getId());
			System.out.println(id);
			//SimpleClient.getClient().sendToServer("#DeleteTime"+ id);
			Time timeToDelete=movie.getScreeningTime().get(index);
			Hall hall=timeToDelete.getHall();
			timeToDelete.setMovie(null);
			movie.getScreeningTime().remove(timeToDelete);
			timeToDelete.setHall(null);
			hall.getScreeningTime().remove(timeToDelete);

			time1.setHall(hall);
			time1.setMovie(movie);
			hall.addScreeningTime(time1);
			movie.getScreeningTime().add(time1);

			SimpleClient.getClient().sendToServer(hall);*/
			SimpleClient.getClient().sendToServer(movie.getScreeningTime().get(index));
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
		/*App.myStage.setScene(App.sceneStack.peek());
		App.myStage.setFullScreen(true);
		App.myStage.show();*/
		/*FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
		Parent root = loader.load();

		loader.setController(App.itemController);

		Scene newscene=new Scene(root);*/
		App.myStage.setScene(App.saveScene);
		App.myStage.setMaximized(true);
		App.myStage.show();
	}

	@FXML
	public void DeleteOP(ActionEvent actionEvent) throws IOException {
    	//App app=new App();
    	//EventBus.getDefault().register(app);
		String id=String.valueOf(movie.getId());
		if(!EventBus.getDefault().isRegistered(this))
			EventBus.getDefault().register(this);
		SimpleClient.getClient().sendToServer("#DeleteMovie"+id);
	}

	@Subscribe
	public void backToProfile(goBack backEvent) throws IOException {
		Platform.runLater(()-> {
			/*FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
			Parent root = null;

				root = loader.load();

			App.itemController = loader.getController();

			Scene newscene=new Scene(root);*/
			App.myStage.setScene(App.saveScene);
			App.myStage.setMaximized(true);
			App.myStage.show();
		});
	}
}
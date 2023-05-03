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
import org.greenrobot.eventbus.EventBus;

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

    private MovieListController movieListController;

    private String type;

    private String fxmlFile;

    private String branch_name;

    public MovieController(){
        this.type= "costumer";
        this.fxmlFile="showmoviecostumer";
    }

    public Movie getMovie(){
        return this.movie;
    }

    public void setMovieListController(MovieListController movieListController){
        this.movieListController=movieListController;
    }

    public void setData(Movie movie1) {
        this.movie=movie1;
        MovieName.setText(movie.getName());
        Image image=new Image(getClass().getResourceAsStream(movie.getImgSrc()));
        btnImage.setImage(image);
    }

    public void setFxmlFile(String fxmlFile){
        this.fxmlFile=fxmlFile;

        if(fxmlFile.startsWith("purchasingticket")) {
            this.fxmlFile = fxmlFile.substring(0, 16);
            branch_name=fxmlFile.substring(16);
            MyBtn.setText("click to purchase tickets");
        }
        else if(fxmlFile.equals("showmoviecostumer"))
            MyBtn.setText("Click for movie details");
        else if(fxmlFile.equals("hometicket"))
            MyBtn.setText("Click to purchase home tickets");
        else if(fxmlFile.equals("#multiTickets"))
            MyBtn.setText("Show Available seats");
    }

    @FXML
    void ImgHit(MouseEvent event) throws IOException {
        if(!fxmlFile.equals("#multiTickets"))
            display();
        else
            movieListController.addToOrderMovies(this);
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
        }else if(fxmlFile.startsWith("purchasingticket")){
            PurchasingTicketController itemController = loader.getController();
            itemController.setData(movie, branch_name);
        } else if (fxmlFile == "hometicket") {
            HomeTicketController itemController = loader.getController();
            itemController.setData(movie);
        }else
            return;

        Scene scene=new Scene(root,600,600);
        App.myStage.setScene(scene);
        App.myStage.setFullScreen(true);
        App.myStage.show();
    }

	@FXML
    public void showIt(javafx.event.ActionEvent actionEvent) throws IOException {
        if(!fxmlFile.equals("#multiTickets"))
            display();
        else{

            FXMLLoader loader = new FXMLLoader(getClass().getResource("availableseats.fxml"));
            Parent root = loader.load();

            AvailableSeatsController itemController = loader.getController();
            itemController.setData(this.movie);

            Scene newscene=new Scene(root);
            Stage stage=new Stage();
            stage.setScene(newscene);
            stage.show();
        }
    }

    @FXML
    public void editIt(javafx.event.ActionEvent actionEvent) throws IOException {

    }
}

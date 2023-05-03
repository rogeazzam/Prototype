package com.example.Prototype.client;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class SecondaryController{

    @FXML
    private VBox adBox;

    @FXML
    private Label movieNameLabel;

    @FXML
    private ImageView ClassicImg;

    @FXML // fx:id="scroll"
    private ScrollPane scroll; // Value injected by FXMLLoader

    @FXML // fx:id="grid"
    private GridPane grid; // Value injected by FXMLLoader

    @FXML
    private GridPane moviesGrid;

    @FXML
    private GridPane loginGrid;
    
    @FXML
    private AnchorPane anchor1;

    @FXML
    private TextField costumerID;

    @FXML
    private TextField verify;

    @FXML
    private Label deniedLabel;
    
    @FXML
    private TextField SearchText;

    @FXML
    private Label logInLabel;
    
    private BranchesList branch;

    private int column=0,row=1;

    private int entered=0;

    private String movies_type="movie list";

    @FXML
    void ListOpen(ActionEvent event) throws IOException {
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        SimpleClient.getClient().sendToServer("#showMovies");
    }

    @FXML
    void BranchOpen(ActionEvent event) throws IOException {
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        SimpleClient.getClient().sendToServer("#showBranches");
    }

    @FXML
    void SearchBtn(ActionEvent event) throws IOException {
    	grid.getChildren().clear();
    	List<Branch> branches1=new ArrayList<Branch>();
    	branches1=branch.getBranches();
        List<Branch> branches2=new ArrayList<Branch>();
    	for(int i=0; i < branches1.size(); i++) {
            if (branches1.get(i).getCity().toLowerCase().contains(SearchText.getText().toLowerCase()))
                branches2.add(branches1.get(i));
        }
    		display(branches2);
    }


	public void setData() throws IOException {
    }

    @FXML
    void LoginDetected(ActionEvent event) throws IOException {
        EventBus.getDefault().register(this);
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(this.getClass().getResource("login.fxml"));
        AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();

        LoginController itemController = fxmlLoader.getController();
        itemController.setData();

        loginGrid.add(anchorPane, 0, 1);
        //set grid width
        loginGrid.setMinWidth(Region.USE_COMPUTED_SIZE);
        loginGrid.setPrefWidth(Region.USE_COMPUTED_SIZE);
        loginGrid.setMaxWidth(Region.USE_PREF_SIZE);

        //set grid height
        loginGrid.setMinHeight(Region.USE_COMPUTED_SIZE);
        loginGrid.setPrefHeight(Region.USE_COMPUTED_SIZE);
        loginGrid.setMaxHeight(Region.USE_PREF_SIZE);

        GridPane.setMargin(anchorPane, new Insets(0, 0, 0, 0));
    }


    @FXML
    void SendCode(ActionEvent event) throws IOException {
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        SimpleClient.getClient().sendToServer("#CheckIfExists"+costumerID.getText());
    }

    @FXML
    void homeWatchOpen(ActionEvent event) throws IOException {
        this.movies_type="home list";
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        SimpleClient.getClient().sendToServer("#HomeWatchList");
    }


    @FXML
    void VerifyCode(ActionEvent event) throws IOException {
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        SimpleClient.getClient().sendToServer("#EnteredVerification" + "%%" + costumerID.getText() +
                                                    "%%" + verify.getText());
    }

    @FXML
    void mostRecent(ActionEvent event) throws IOException {
        this.movies_type="movies to come";
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        SimpleClient.getClient().sendToServer("#showMovies");
    }

    private void sort(List<Movie> movieList){
        for(int i=0; i < movieList.size()-1; i++){
            for(int j=0; j< movieList.size()-i-1; j++){
                if(movieList.get(j).getScreeningTime().get(0).greater(movieList.get(j+1).getScreeningTime().get(0))) {
                    Collections.swap(movieList, j, j+1);
                }
            }
        }

        while(movieList.size() > 5){
            movieList.remove(movieList.size()-1);
        }
    }


    @FXML
    void multiTicketsOpen(ActionEvent event) throws IOException {
        this.movies_type="#multiTickets";
        if(!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        SimpleClient.getClient().sendToServer("#showMovies");
    }

    @Subscribe
    public void onMovieListEvent(MovieListEvent event){
        Platform.runLater(()->{
            MovieList movies= event.getMovies();
            try {
                if(EventBus.getDefault().isRegistered(this))
                    EventBus.getDefault().unregister(this);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("movielist.fxml"));
                Parent root = loader.load();

                MovieListController itemController = loader.getController();
                if(this.movies_type.equals("movie list"))
                    itemController.setData(movies);
                else if(this.movies_type.equals("home list"))
                    itemController.setData(movies, "hometicket");
                else if(this.movies_type.equals("movies to come")){
                    List<Movie> movieList=new ArrayList<Movie>();
                    movieList.addAll(movies.getMovies());
                    sort(movieList);
                    MovieList newMovies=new MovieList();
                    newMovies.setMovies(movieList);
                    itemController.setData(newMovies, this.movies_type);
                }else if(this.movies_type.equals("#multiTickets"))
                    itemController.setData(movies, this.movies_type);

                Scene scene=new Scene(root);
                saveScene=scene;
                App.myStage.setScene(scene);
                App.myStage.setFullScreen(true);
                App.myStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    static ProfileController onLoginController;
    static Scene saveScene;

    @Subscribe
    public void onLoginDenied(StringReceiver str){
        Platform.runLater(()->{
            if(EventBus.getDefault().isRegistered(this))
                EventBus.getDefault().unregister(this);
            if(str.getStr().equals("#NotVerified"))
                deniedLabel.setText("verification code is not correct!");
            else if(str.getStr().equals("User Already Signed In!"))
                logInLabel.setText(str.getStr());
        });

    }

    @Subscribe
    public void onLogin(Person person){
        Platform.runLater(()->{
            try {
                if(EventBus.getDefault().isRegistered(this))
                    EventBus.getDefault().unregister(this);

                FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
                Parent root = loader.load();

                onLoginController = loader.getController();
                onLoginController.setData(person);

                Scene newscene=new Scene(root);
                saveScene=newscene;
                App.myStage.setScene(newscene);
                App.myStage.setMaximized(true);
                App.myStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        EventBus.getDefault().unregister(this);
    }


    @Subscribe
    public void costumerProfile(Costumer costumer){
        Platform.runLater(()->{
            try {
                if(EventBus.getDefault().isRegistered(this))
                    EventBus.getDefault().unregister(this);
                FXMLLoader loader = new FXMLLoader(getClass().getResource("costumerprofile.fxml"));
                Parent root = loader.load();

                CostumerProfileController itemController = loader.getController();
                itemController.setData(costumer);

                EventBus.getDefault().unregister(this);

                Scene newscene=new Scene(root);
                App.myStage.setScene(newscene);
                App.myStage.setMaximized(true);
                App.myStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    @Subscribe
    public void onBranchListEvent(BranchesListEvent event) throws IOException {
        Platform.runLater(()->{
            if(EventBus.getDefault().isRegistered(this))
                EventBus.getDefault().unregister(this);
            BranchesList branches= event.getBranches();
            this.branch=branches;
            List<Branch> branchList=branches.getBranches();
            try {
                display(branchList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    void display(List<Branch> branches) throws IOException {
        column=0;
        row=1;
        for(Branch branch:branches) {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(this.getClass().getResource("branches.fxml"));
            AnchorPane anchorPane = (AnchorPane) fxmlLoader.load();

            BranchesController itemController = fxmlLoader.getController();
            itemController.setData(branch);

            grid.add(anchorPane, column, row);
            //set grid width
            grid.setMinWidth(Region.USE_COMPUTED_SIZE);
            grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
            grid.setMaxWidth(Region.USE_PREF_SIZE);

            //set grid height
            grid.setMinHeight(Region.USE_COMPUTED_SIZE);
            grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
            grid.setMaxHeight(Region.USE_PREF_SIZE);

            GridPane.setMargin(anchorPane, new Insets(0, 0, 10, 0));
            row++;
        }
    }

}

package com.example.Prototype.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;
import java.util.Stack;

/**
 * JavaFX App
 */

public class App extends Application {

    private static Scene scene;
    public static Stage myStage;
    private SimpleClient client;
    public static Stack<Scene> sceneStack=new Stack<Scene>();

    @Override
    public void start(Stage stage) throws IOException {
    	EventBus.getDefault().register(this);
    	client = SimpleClient.getClient();
    	client.openConnection();
        scene = new Scene(loadFXML("primary"));
        stage.setScene(scene);
        App.sceneStack.push(scene);
        App.myStage=stage;
        App.myStage.setMaximized(true);
        myStage.show();
    }

    public void register(){
        EventBus.getDefault().register(this);
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

    @Subscribe
    public void onMovieListEvent(MovieListEvent event){
        Platform.runLater(()->{
            MovieList movies= event.getMovies();
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("movielist.fxml"));
                Parent root = loader.load();

                MovieListController itemController = loader.getController();
                itemController.setData(movies);

                App.myStage.setScene(new Scene(root));
                App.myStage.setMaximized(true);
                App.myStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    static ProfileController itemController;
    static Scene saveScene;

    @Subscribe
    public void onLogin(Person person){
        Platform.runLater(()->{
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("profile.fxml"));
                Parent root = loader.load();

                itemController = loader.getController();
                itemController.setData(person);

                Scene newscene=new Scene(root);
                saveScene=newscene;
                App.myStage.setScene(newscene);
                App.myStage.setMaximized(true);
                sceneStack.push(newscene);
                App.myStage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        EventBus.getDefault().unregister(this);
    }
}
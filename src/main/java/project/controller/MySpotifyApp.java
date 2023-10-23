package project.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MySpotifyApp extends Application {
    @Override
    public void start(final Stage primaryStage) throws Exception {
        primaryStage.setTitle("Brosjan Music");
        primaryStage.setScene(new Scene(
                FXMLLoader.load(this.getClass().getResource("/project/view/MySpotifyApp.fxml"))));
        primaryStage.show();
    }

    public static void main(final String[] args) {
        Application.launch(args);
    }
}

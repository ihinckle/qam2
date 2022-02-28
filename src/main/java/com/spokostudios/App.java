package com.spokostudios;

import com.spokostudios.services.ViewsService;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    static ViewsService vs;

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        vs = ViewsService.getInstance();

        scene = new Scene(vs.loadFXML("login"), 1440, 900);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(vs.loadFXML(fxml));
    }

    public static void main(String[] args) {
        launch();
    }

}
/*
* File: App.java
* Author: Vámosi László Ádám
* Copyright: 2025, Vámosi László Ádám
* Group: Szoft I-N
* Date: 2025-05-20
* GitHub: https://github.com/vamosilaszloadam/
* Licenc: MIT
*/

package com.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static Stage _stage;

    @Override
    public void start(Stage stage) throws IOException {
        _stage = stage;
        scene = new Scene(loadFXML("mainScene"), 640, 480);
        stage.setScene(scene);
        stage.setTitle("Hengfel");
        stage.setResizable(false);
        loadIcon("img.png");
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }
    private void loadIcon(String fileName){
        try {
            _stage.getIcons().add(new Image(getClass().getResourceAsStream(fileName)));
        } catch (NullPointerException e) {
            System.err.println(e.getMessage());
        }
    }
}
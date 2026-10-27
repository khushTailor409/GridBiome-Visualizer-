package com.mycompany.gridbiomevisualizer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Label title = new Label("GridBiome Visualizer");
        Label introduction = new Label("Generate terrain, explore biomes, and simulate weather.");
        Button playButton = new Button("Play");
        
        VBox openingScreen = new VBox(22, title, introduction, playButton);
        
        Scene scene = new Scene(openingScreen, 960, 760);
        stage.setTitle("GridBiome Visualizer");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }

}
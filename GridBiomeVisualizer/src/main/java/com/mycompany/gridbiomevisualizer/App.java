package com.mycompany.gridbiomevisualizer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {
    private Scene scene;

    @Override
    public void start(Stage stage) {
        Label title = new Label("GridBiome Visualizer");
        Label introduction = new Label("Generate terrain, explore biomes, and simulate weather.");
        Button playButton = new Button("Play");
        
        playButton.setPrefWidth(150);
        
        VBox openingScreen = new VBox(22, title, introduction, playButton);
        openingScreen.setAlignment(Pos.CENTER);
        openingScreen.setPadding(new Insets(30));
        
        scene = new Scene(openingScreen, 960, 760);
        stage.setTitle("GridBiome Visualizer");
        stage.setScene(scene);
        stage.show();
    }
    
    private void showMainInterface() {
        BorderPane mainScreen = new BorderPane();
        Label mainTitle = new Label("GridBiome Visualizer");
        
        scene.setRoot(mainScreen);
    }

    public static void main(String[] args) {
        launch();
    }

}
package com.mycompany.gridbiomevisualizer;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


/** testing
 * JavaFX App
 */
public class App extends Application {
    private Scene scene;

    @Override
    public void start(Stage stage) {
        Label title = new Label("GridBiome Visualizer");
        title.getStyleClass().add("opening-title");
        Label introduction = new Label("Generate terrain, explore biomes, and simulate weather.");
        Button playButton = new Button("Play");
        
        playButton.setId("play");
        playButton.setPrefWidth(150);
        playButton.setDefaultButton(true);
        playButton.setOnAction(e -> showMainInterface());
        
        VBox openingScreen = new VBox(22, title, introduction, playButton);
        openingScreen.setAlignment(Pos.CENTER);
        openingScreen.setPadding(new Insets(30));
        
        scene = new Scene(openingScreen, 960, 760);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());
        stage.setTitle("GridBiome Visualizer");
        stage.setMinWidth(900);
        stage.setMinHeight(700);
        stage.setScene(scene);
        stage.show();
    }
    
    private void showMainInterface() {
        scene.setRoot(new TerrainGrid());
    }

    public static void main(String[] args) {
        launch();
    }

}
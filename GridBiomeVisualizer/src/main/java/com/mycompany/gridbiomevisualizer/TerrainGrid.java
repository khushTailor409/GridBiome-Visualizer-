package com.mycompany.gridbiomevisualizer;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * JavaFX TerrainGrid
 */
public class TerrainGrid extends BorderPane {
    private final Label positionLabel = new Label("Position: --");
    private final Label altitudeLabel = new Label("Altitude: --");
    private final Label biomeLabel = new Label("Biome: --");
    private final Label temperatureLabel = new Label("Temperature: --");
    private final Label precipitationLabel = new Label("Precipitation: --");
    private final Label weatherLabel = new Label("Weather: --");
    private final Label statusLabel = new Label("No terrain generated.");
    
    public TerrainGrid() {
        Label title = new Label("GridBiome Visualizer");
        VBox header = new VBox(12, title);
        
        setPadding(new Insets(18));
        setTop(header);
        BorderPane.setMargin(header, new Insets(0, 0, 16, 0));
        
        VBox information = new VBox(14, new Label("Selected cell"), positionLabel, altitudeLabel, biomeLabel, temperatureLabel, precipitationLabel, weatherLabel);
        information.setPadding(new Insets(16));
        setRight(information);
        BorderPane.setMargin(information, new Insets(0, 0, 16, 0));
        setBottom(statusLabel);
    }
}

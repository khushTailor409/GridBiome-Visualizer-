package com.mycompany.gridbiomevisualizer;

import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

/**
 * JavaFX TerrainGrid
 */
public class TerrainGrid extends BorderPane {
    Label title = new Label("GridBiome Visualizer");
    VBox header = new VBox(12, title);
}

package com.mycompany.gridbiomevisualizer;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * JavaFX TerrainGrid
 */
public class TerrainGrid extends BorderPane {
    private static final int GRID_SIZE = 10;
    private final Button[][] cells = new Button[GRID_SIZE][GRID_SIZE];
    private final Label positionLabel = new Label("Position: --");
    private final Label altitudeLabel = new Label("Altitude: --");
    private final Label biomeLabel = new Label("Biome: --");
    private final Label temperatureLabel = new Label("Temperature: --");
    private final Label precipitationLabel = new Label("Precipitation: --");
    private final Label weatherLabel = new Label("Weather: --");
    private final Label statusLabel = new Label("No terrain generated.");
    
    public TerrainGrid() {
        Label title = new Label("GridBiome Visualizer");
        title.getStyleClass().add("main-title");
        VBox header = new VBox(12, title);
        
        setPadding(new Insets(18));
        setTop(header);
        BorderPane.setMargin(header, new Insets(0, 0, 16, 0));
        
        VBox information = new VBox(14, new Label("Selected cell"), positionLabel, altitudeLabel, biomeLabel, temperatureLabel, precipitationLabel, weatherLabel);
        information.setPrefWidth(215);
        information.setMinWidth(215);
        information.setPadding(new Insets(16));
        information.getStyleClass().add("information-panel");
        setRight(information);
        BorderPane.setMargin(information, new Insets(0, 0, 16, 0));
        statusLabel.setWrapText(true);
        setBottom(statusLabel);
        
        altitudeLabel.setId("info-altitude");
        biomeLabel.setId("info-biome");
        weatherLabel.setId("info-weather");
        positionLabel.setId("info-position");
        statusLabel.setId("status");
        
        StackPane gridHolder = new StackPane(createGrid());
        setCenter(gridHolder);
        BorderPane.setMargin(gridHolder, new Insets(0, 16, 16, 0));
    }
    
    private Pane createGrid() {
        Pane grid = new Pane() {
            @Override
            protected void layoutChildren() {
                double side = Math.min(getWidth(), getHeight());
                double cellSize = side / GRID_SIZE;
                double left = (getWidth() - side) / 2;
                double top = (getHeight() - side) / 2;
                
                for (int row = 0; row < GRID_SIZE; row++) {
                    for (int col = 0; col < GRID_SIZE; col++) {
                        cells[row][col].resizeRelocate(left + col * cellSize, top + row * cellSize, cellSize, cellSize);
                    }
                }
            }
        };
        
        grid.setPrefSize(500,500);
        grid.setMinSize(350, 350);
        grid.setMaxSize(600, 600);
        
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                Button cell = new Button();
                
                cell.setManaged(false);
                cell.setMinSize(0, 0);
                cell.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
                cell.setId("cell-" + row + "-" + col);
                cell.setAccessibleText("Row " + (row + 1) + ", column " + (col + 1) + ": no terrain");
                cell.getStyleClass().add("terrain-cell");
                final int cellRow = row;
                final int cellCol = col;
                cell.setOnAction(e -> selectCell(cellRow, cellCol));
                cells[row][col] = cell;
                grid.getChildren().add(cell);
            }
        }
        return grid;
    }
    
    private void selectCell(int row, int col) {
        statusLabel.setText("No terrain exists yet. Cell information will appear after generation.");
    }
}

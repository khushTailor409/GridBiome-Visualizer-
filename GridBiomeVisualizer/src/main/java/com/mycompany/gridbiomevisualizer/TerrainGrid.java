package com.mycompany.gridbiomevisualizer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/**
 * JavaFX TerrainGrid
 */
public class TerrainGrid extends BorderPane {
    private static final int GRID_SIZE = TerrainGenerator.GRID_SIZE;
    private final Button[][] cells = new Button[GRID_SIZE][GRID_SIZE];
    private final Label positionLabel = new Label("Position: --");
    private final Label altitudeLabel = new Label("Altitude: --");
    private final Label biomeLabel = new Label("Biome: --");
    private final Label temperatureLabel = new Label("Temperature: --");
    private final Label precipitationLabel = new Label("Precipitation: --");
    private final Label weatherLabel = new Label("Weather: --");
    private final Label statusLabel = new Label("No terrain generated.");
    private int selectedRow = -1;
    private int selectedCol = -1;
    private final Button generateButton = new Button("Generate Terrain");
    private final Button weatherButton = new Button("Simulate Weather");
    private final Label legendTitle = new Label("Terrain legend (altitude)");
    private final FlowPane legendItems = new FlowPane(14, 8);
    private final TerrainGenerator generator = new TerrainGenerator();
    private int[][] altitudes;

    public TerrainGrid() {
        setPadding(new Insets(18));
        altitudeLabel.setId("info-altitude");
        biomeLabel.setId("info-biome");
        weatherLabel.setId("info-weather");
        positionLabel.setId("info-position");
        statusLabel.setId("status");
        
        Label title = new Label("GridBiome Visualizer");
        title.getStyleClass().add("main-title");
        VBox header = new VBox(12, title);
        
        generateButton.setId("generate");
        weatherButton.setId("simulate-weather");
        weatherButton.setDisable(true);
        generateButton.setDisable(true);
        statusLabel.setText("No terrain generated. Generation will be connected in the next stage.");
        weatherButton.setTooltip(new Tooltip("Weather simulation is connected in stage 6."));
        
        header.getChildren().add(new FlowPane(12, 8, generateButton, weatherButton));
        setTop(header);
        BorderPane.setMargin(header, new Insets(0, 0, 16, 0));

        StackPane gridHolder = new StackPane(createGrid());
        setCenter(gridHolder);
        BorderPane.setMargin(gridHolder, new Insets(0, 16, 16, 0));

        VBox information = new VBox(14,
        new Label("Selected cell"), positionLabel, altitudeLabel,
        biomeLabel, temperatureLabel, precipitationLabel, weatherLabel);
        
        information.setPrefWidth(215);
        information.setMinWidth(215);
        information.setPadding(new Insets(16));
        information.getStyleClass().add("information-panel");
        
        setRight(information);
        BorderPane.setMargin(information, new Insets(0, 0, 16, 0));
        
        statusLabel.setWrapText(true);
        setBottom(new VBox(10, legendTitle, legendItems, statusLabel));
        updateLegend();
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
                        cells[row][col].resizeRelocate(
                                left + col * cellSize, top + row * cellSize,
                                cellSize, cellSize);
                    }
                }
            }
        };
        
        grid.setPrefSize(500, 500);
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
                
                cell.setOnAction(event -> selectCell(cellRow, cellCol));
                cells[row][col] = cell;
                grid.getChildren().add(cell);
            }
        }
        return grid;
    }

    private void selectCell(int row, int col) {
        statusLabel.setText("No terrain exists yet. Cell information will appear after generation.");
    }
    
    private void addLegendItem(String text, String styleClass) {
        Label swatch = new Label();
        
        swatch.setMinSize(16, 16);
        swatch.setPrefSize(16, 16);
        swatch.setMaxSize(16, 16);
        swatch.getStyleClass().addAll("legend-swatch", styleClass);
        
        HBox item = new HBox(6, swatch, new Label(text));
        item.setAlignment(Pos.CENTER_LEFT);
        legendItems.getChildren().add(item);
    }
    
    private void updateLegend() {
        legendItems.getChildren().clear();
        legendTitle.setText("Terrain legend (altitude)");
        
        for (BiomeMapper.Biome biome : BiomeMapper.Biome.values()) {
            addLegendItem(biome.getDisplayName() + " " + biome.getRange(),
                    "biome-" + biome.name().toLowerCase(java.util.Locale.ROOT));
        }
    }

    
    private void renderCells() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int altitude = altitudes[row][col];
                
                BiomeMapper.Biome biome = BiomeMapper.classify(altitude);
                String text = Integer.toString(altitude);
                String styleClass = "biome-" + biome.name().toLowerCase(java.util.Locale.ROOT);
                String description = "Row " + (row + 1) + ", column " + (col + 1)
                        + ": altitude " + altitude + ", " + biome.getDisplayName();
                
                cells[row][col].setText(text);
                cells[row][col].setAccessibleText(description);
                cells[row][col].getStyleClass().setAll("button", "terrain-cell", styleClass);
                
                if (row == selectedRow && col == selectedCol) {
                    cells[row][col].getStyleClass().add("selected");
                }
            }
        }
    }
    
    
}
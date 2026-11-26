package com.mycompany.gridbiomevisualizer;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.control.*;
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
    private final WeatherSimulator simulator = new WeatherSimulator();
    private WeatherSimulator.WeatherData[][] weatherData;
    private final RadioButton terrainView = new RadioButton("Terrain");
    private final RadioButton weatherView = new RadioButton("Weather");
    private final Label averageTemperatureStatistic = new Label("Average temperature: --");
    private final Label temperatureRangeStatistic = new Label("Temperature range: --");
    private final Label averagePrecipitationStatistic = new Label("Average precipitation: --");
    private final Label totalCellsStatistic = new Label("Cells counted: --");
    private final Label conditionCountsStatistic = new Label("Conditions: --");
    private final Label averageAltitudeStatistic = new Label("Average altitude: --");
    private final Label highestAltitudeStatistic = new Label("Highest cell: --");
    private final Label lowestAltitudeStatistic = new Label("Lowest cell: --");
    private final Label biomeCountsStatistic = new Label("Biomes: --");

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
        generateButton.setOnAction(e -> generateTerrain());
        weatherButton.setOnAction(event -> simulateWeather());
        ToggleGroup viewGroup = new ToggleGroup();
        terrainView.setToggleGroup(viewGroup);
        weatherView.setToggleGroup(viewGroup);
        terrainView.setId("terrain-view");
        weatherView.setId("weather-view");
        terrainView.setSelected(true);
        weatherView.setDisable(true);
        terrainView.setOnAction(event -> refreshView());
        weatherView.setOnAction(event -> refreshView());

        header.getChildren().addAll(
                new FlowPane(12, 8, generateButton, weatherButton),
                new HBox(12, new Label("View:"),terrainView, weatherView)
        );

        setTop(header);
        BorderPane.setMargin(header, new Insets(0, 0, 16, 0));


        StackPane gridHolder = new StackPane(createGrid());
        setCenter(gridHolder);
        BorderPane.setMargin(gridHolder, new Insets(0, 16, 16, 0));
        
        Label selectedCellTitle = new Label("Selected cell");
        Label terrainStatisticsTitle = new Label("Terrain statistics");
        Label weatherStatisticsTitle = new Label("Weather statistics");
        
        VBox information = new VBox(10,
        selectedCellTitle,
        positionLabel, altitudeLabel, biomeLabel,
        temperatureLabel, precipitationLabel, weatherLabel,
        terrainStatisticsTitle,
        averageAltitudeStatistic,
        highestAltitudeStatistic,
        lowestAltitudeStatistic,
        biomeCountsStatistic,
        weatherStatisticsTitle,
        averageTemperatureStatistic,
        temperatureRangeStatistic,
        averagePrecipitationStatistic,
        totalCellsStatistic,
        conditionCountsStatistic);

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
        if (altitudes == null) {
            statusLabel.setText("Generate terrain before selecting a cell.");
            return;
        }

        selectedRow = row;
        selectedCol = col;

        renderCells();
        updateInformation();

        statusLabel.setText(
                "Selected row " + (row + 1)
                        + ", column " + (col + 1) + "."
        );
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
        if (weatherView.isSelected() && weatherData != null) {
            legendTitle.setText("Weather legend");
            for (WeatherSimulator.Condition condition : WeatherSimulator.Condition.values()) {
                addLegendItem(condition.getDisplayName(),
                        "weather-" + condition.name().toLowerCase(java.util.Locale.ROOT));
            }
            return;
        }
        legendTitle.setText("Terrain legend (altitude)");
        for (BiomeMapper.Biome biome : BiomeMapper.Biome.values()) {
            addLegendItem(biome.getDisplayName() + " " + biome.getRange(),
                    "biome-" + biome.name().toLowerCase(java.util.Locale.ROOT));
        }
    }

    private boolean hasTerrainCell(int row, int col) {
        return altitudes != null && row >= 0 && row < altitudes.length
                && altitudes[row] != null && col >= 0
                && col < altitudes[row].length
                && altitudes[row][col] >= 0 && altitudes[row][col] <= 100;
    }

    private boolean hasWeatherCell(int row, int col) {
        return weatherData != null && row >= 0 && row < weatherData.length
                && weatherData[row] != null && col >= 0
                && col < weatherData[row].length && weatherData[row][col] != null
                && weatherData[row][col].getCondition() != null;
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
                if (weatherView.isSelected() && weatherData != null) {
                    WeatherSimulator.Condition condition = weatherData[row][col].getCondition();
                    text = condition.getDisplayName();
                    styleClass = "weather-" + condition.name().toLowerCase(java.util.Locale.ROOT);
                    description += ", " + condition.getDisplayName();
                }
                cells[row][col].setText(text);
                cells[row][col].setAccessibleText(description);
                cells[row][col].getStyleClass().setAll("button", "terrain-cell", styleClass);

                if (row == selectedRow && col == selectedCol) {
                    cells[row][col].getStyleClass().add("selected");
                }
            }
        }
    }

    private void updateInformation() {
        clearInformation();
        if (altitudes == null || selectedRow < 0 || selectedCol < 0
                || selectedRow >= altitudes.length
                || altitudes[selectedRow] == null
                || selectedCol >= altitudes[selectedRow].length) return;
        if (altitudes[selectedRow][selectedCol] < 0
                || altitudes[selectedRow][selectedCol] > 100) return;
        int altitude = altitudes[selectedRow][selectedCol];
        positionLabel.setText("Position: " + (selectedRow + 1) + ", " + (selectedCol + 1));
        altitudeLabel.setText("Altitude: " + altitude);
        biomeLabel.setText("Biome: " + BiomeMapper.classify(altitude).getDisplayName());
        if (hasWeatherCell(selectedRow, selectedCol)) {
            WeatherSimulator.WeatherData data = weatherData[selectedRow][selectedCol];
            temperatureLabel.setText(String.format(java.util.Locale.ROOT,
                    "Temperature: %.1f °C", data.getTemperature()));
            precipitationLabel.setText(String.format(java.util.Locale.ROOT,
                    "Precipitation: %.1f%%", data.getPrecipitation()));
            weatherLabel.setText("Weather: " + data.getCondition().getDisplayName());
        }
    }

    private void clearInformation() {
        positionLabel.setText("Position: --");
        altitudeLabel.setText("Altitude: --");
        biomeLabel.setText("Biome: --");
        temperatureLabel.setText("Temperature: --");
        precipitationLabel.setText("Precipitation: --");
        weatherLabel.setText("Weather: --");
    }


    private void simulateWeather() {
        if (altitudes == null) {
            statusLabel.setText("Generate terrain before simulating weather.");
            return;
        }

        try {
            weatherData = simulator.simulate(altitudes);
            updateWeatherStatistics();
            weatherView.setDisable(false);
            weatherView.setSelected(true);
            refreshView();
            statusLabel.setText("Weather simulated. Select a cell or switch back to Terrain.");
        } catch (IllegalArgumentException exception) {
            weatherData = null;
            terrainView.setSelected(true);
            weatherView.setDisable(true);
            clearWeatherStatistics();
            refreshView();
            statusLabel.setText("Weather could not be simulated: " + exception.getMessage());
        }
    }
    
    private void updateTerrainStatistics() {
        TerrainStatistics statistics = TerrainStatistics.calculate(altitudes);

        averageAltitudeStatistic.setText(String.format(java.util.Locale.ROOT,
                "Average altitude: %.1f", statistics.getAverageAltitude()));
        
        highestAltitudeStatistic.setText(
                "Highest cell: " + statistics.getHighestAltitude()
                + " at row " + (statistics.getHighestRow() + 1)
                + ", column " + (statistics.getHighestColumn() + 1));
        
        lowestAltitudeStatistic.setText(
                "Lowest cell: " + statistics.getLowestAltitude()
                + " at row " + (statistics.getLowestRow() + 1)
                + ", column " + (statistics.getLowestColumn() + 1));
        
        biomeCountsStatistic.setText(String.format(java.util.Locale.ROOT,
                "Biomes: Ocean %d, Beach %d, Grassland %d, Forest %d, Mountain %d",
                statistics.getOceanCount(), statistics.getBeachCount(),
                statistics.getGrasslandCount(), statistics.getForestCount(),
                statistics.getMountainCount()));
    }
    
    private void clearTerrainStatistics() {
        averageAltitudeStatistic.setText("Average altitude: --");
        highestAltitudeStatistic.setText("Highest cell: --");
        lowestAltitudeStatistic.setText("Lowest cell: --");
        biomeCountsStatistic.setText("Biomes: --");
    }
    
    private void updateWeatherStatistics() {
        WeatherStatistics statistics = WeatherStatistics.calculate(weatherData);

        averageTemperatureStatistic.setText(String.format(java.util.Locale.ROOT,
                "Average temperature: %.1f °C", statistics.getAverageTemperature()));
        
        temperatureRangeStatistic.setText(String.format(java.util.Locale.ROOT,
                "Temperature range: %.1f to %.1f °C",
                statistics.getMinimumTemperature(), statistics.getMaximumTemperature()));
        
        averagePrecipitationStatistic.setText(String.format(java.util.Locale.ROOT,
                "Average precipitation: %.1f%%", statistics.getAveragePrecipitation()));
        
        totalCellsStatistic.setText("Cells counted: " + statistics.getTotalCells());
        
        conditionCountsStatistic.setText(String.format(java.util.Locale.ROOT,
                "Conditions: Clear %d, Cloudy %d, Rain %d, Snow %d",
                statistics.getClearCount(), statistics.getCloudyCount(),
                statistics.getRainCount(), statistics.getSnowCount()));
        }
    
    private void clearWeatherStatistics() {
        averageTemperatureStatistic.setText("Average temperature: --");
        temperatureRangeStatistic.setText("Temperature range: --");
        averagePrecipitationStatistic.setText("Average precipitation: --");
        totalCellsStatistic.setText("Cells counted: --");
        conditionCountsStatistic.setText("Conditions: --");
}
    
    private void refreshView() {
        if (altitudes == null) return;
        renderCells();
        updateLegend();
        updateInformation();
    }


    private void generateTerrain() {
        altitudes = generator.generate();
        selectedRow = -1;
        selectedCol = -1;
        weatherData = null;

        terrainView.setSelected(true);
        terrainView.setDisable(false);
        weatherView.setDisable(true);
        weatherButton.setDisable(false);

        updateTerrainStatistics();
        clearWeatherStatistics();
        updateLegend();
        clearInformation();
        renderCells();
        statusLabel.setText("New terrain generated. Select a cell to inspect it.");
    }
}
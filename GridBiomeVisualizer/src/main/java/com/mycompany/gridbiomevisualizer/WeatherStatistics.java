/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gridbiomevisualizer;

/**
 *
 * @author BAF
 */
public class WeatherStatistics {
    private final double averageTemperature;
    private final double minimumTemperature;
    private final double maximumTemperature;
    private final double averagePrecipitation;
    private final int totalCells;
    private final int clearCount;
    private final int cloudyCount;
    private final int rainCount;
    private final int snowCount;
    
     private WeatherStatistics(double averageTemperature, 
            double minimumTemperature, double maximumTemperature,
            double averagePrecipitation, int totalCells,
            int clearCount, int cloudyCount, int rainCount, int snowCount) {
         
        this.averageTemperature = averageTemperature;
        this.minimumTemperature = minimumTemperature;
        this.maximumTemperature = maximumTemperature;
        this.averagePrecipitation = averagePrecipitation;
        this.totalCells = totalCells;
        this.clearCount = clearCount;
        this.cloudyCount = cloudyCount;
        this.rainCount = rainCount;
        this.snowCount = snowCount;
    }
    
     public static WeatherStatistics calculate(WeatherSimulator.WeatherData[][] weatherData) {
        if (weatherData == null || weatherData.length == 0) {
            throw new IllegalArgumentException("Weather data cannot be empty.");
        }

        double temperatureSum = 0;
        double precipitationSum = 0;
        double minimumTemperature = Double.POSITIVE_INFINITY;
        double maximumTemperature = Double.NEGATIVE_INFINITY;
        int totalCells = 0;
        int clearCount = 0;
        int cloudyCount = 0;
        int rainCount = 0;
        int snowCount = 0;

        for (int row = 0; row < weatherData.length; row++) {
            if (weatherData[row] == null) {
                throw new IllegalArgumentException("Weather data rows cannot be null.");
            }

            for (int col = 0; col < weatherData[row].length; col++) {
                WeatherSimulator.WeatherData cell = weatherData[row][col];
                if (cell == null) {
                    throw new IllegalArgumentException("Weather data cells cannot be null.");
                }

                double temperature = cell.getTemperature();
                double precipitation = cell.getPrecipitation();

                temperatureSum += temperature;
                precipitationSum += precipitation;
                minimumTemperature = Math.min(minimumTemperature, temperature);
                maximumTemperature = Math.max(maximumTemperature, temperature);
                totalCells++;

                switch (cell.getCondition()) {
                    case CLEAR:
                        clearCount++;
                        break;
                    case CLOUDY:
                        cloudyCount++;
                        break;
                    case RAIN:
                        rainCount++;
                        break;
                    case SNOW:
                        snowCount++;
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown weather condition.");
                }
            }
        } 
     
    public double getAverageTemperature() {
        return averageTemperature;
    }

    public double getMinimumTemperature() {
        return minimumTemperature;
    }

    public double getMaximumTemperature() {
        return maximumTemperature;
    }

    public double getAveragePrecipitation() {
        return averagePrecipitation;
    }

    public int getTotalCells() {
        return totalCells;
    }

    public int getClearCount() {
        return clearCount;
    }

    public int getCloudyCount() {
        return cloudyCount;
    }

    public int getRainCount() {
        return rainCount;
    }

    public int getSnowCount() {
        return snowCount;
    } 
}

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
}

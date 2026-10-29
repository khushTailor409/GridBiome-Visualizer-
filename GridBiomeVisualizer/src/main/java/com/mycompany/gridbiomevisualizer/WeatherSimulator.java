/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gridbiomevisualizer;

import java.util.Random;

/**
 *
 * @author BAF
 */
public class WeatherSimulator {
    public enum Condition {
        CLEAR("Clear"),
        CLOUDY("Cloudy"),
        RAIN("Rain"),
        SNOW("Snow");

        private final String displayName;

        Condition(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() { 
            return displayName; 
        }
    }
    
    public static final class WeatherData {
        private final double temperature;
        private final double precipitation;
        private final Condition condition;

        private WeatherData(double temperature, double precipitation, Condition condition) {
            this.temperature = temperature;
            this.precipitation = precipitation;
            this.condition = condition;
        }

        public double getTemperature() { 
            return temperature; 
        }
        
        public double getPrecipitation() { 
            return precipitation; 
        }
        
        public Condition getCondition() { 
            return condition;
        }
        
    }
    private final Random random;

    public WeatherSimulator() {
        this(new Random());
    }

    public WeatherSimulator(Random random) {
        this.random = random;
    }

    public WeatherData[][] simulate(int[][] altitudes) {
        WeatherData[][] results = new WeatherData[altitudes.length][];
        for (int row = 0; row < altitudes.length; row++) {
            results[row] = new WeatherData[altitudes[row].length];
            for (int col = 0; col < altitudes[row].length; col++) {
                int variation = random.nextInt(31) - 15;
                results[row][col] = calculate(altitudes[row][col], variation);
            }
        }
        return results;
    }
}

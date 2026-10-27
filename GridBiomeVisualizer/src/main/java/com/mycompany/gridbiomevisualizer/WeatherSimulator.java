/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.gridbiomevisualizer;

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
}

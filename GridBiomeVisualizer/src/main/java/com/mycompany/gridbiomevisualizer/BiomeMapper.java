package com.mycompany.gridbiomevisualizer;

/**
 * JavaFX BiomeMapper
 */
public final class BiomeMapper {
    public enum Biome {
        OCEAN("Ocean", "0-20"),
        BEACH("Beach", "21-30"),
        GRASSLAND("Grassland", "31-60"),
        FOREST("Forest", "61-80"),
        MOUNTAIN("Mountain", "81-100");
        
        private final String displayName;
        private final String range;
        
        Biome(String displayName, String range) {
            this.displayName = displayName;
            this.range = range;
        }
        
        public String getDisplayName() {
            return displayName;
        }
        
        public String getRange() {
            return range;
        }
    }
    
    private BiomeMapper() { }

    public static Biome classify(int altitude) {
        if (altitude < 0 || altitude > 100) {
            throw new IllegalArgumentException("Altitude must be between 0 and 100.");
        }
        if (altitude <= 20) return Biome.OCEAN;
        if (altitude <= 30) return Biome.BEACH;
        if (altitude <= 60) return Biome.GRASSLAND;
        if (altitude <= 80) return Biome.FOREST;
        return Biome.MOUNTAIN;
    }
    
}

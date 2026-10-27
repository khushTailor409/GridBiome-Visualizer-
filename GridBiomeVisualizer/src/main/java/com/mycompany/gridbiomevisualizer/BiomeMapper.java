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
    }
    
}

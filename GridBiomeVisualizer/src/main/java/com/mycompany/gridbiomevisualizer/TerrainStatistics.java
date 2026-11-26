package com.mycompany.gridbiomevisualizer;

public final class TerrainStatistics {
    private final double averageAltitude;
    private final int lowestAltitude;
    private final int lowestRow;
    private final int lowestColumn;
    private final int highestAltitude;
    private final int highestRow;
    private final int highestColumn;
    private final int oceanCount;
    private final int beachCount;
    private final int grasslandCount;
    private final int forestCount;
    private final int mountainCount;

    private TerrainStatistics(double averageAltitude,
            int lowestAltitude, int lowestRow, int lowestColumn,
            int highestAltitude, int highestRow, int highestColumn,
            int oceanCount, int beachCount, int grasslandCount,
            int forestCount, int mountainCount) {
        this.averageAltitude = averageAltitude;
        this.lowestAltitude = lowestAltitude;
        this.lowestRow = lowestRow;
        this.lowestColumn = lowestColumn;
        this.highestAltitude = highestAltitude;
        this.highestRow = highestRow;
        this.highestColumn = highestColumn;
        this.oceanCount = oceanCount;
        this.beachCount = beachCount;
        this.grasslandCount = grasslandCount;
        this.forestCount = forestCount;
        this.mountainCount = mountainCount;
    }
    
    public static TerrainStatistics calculate(int[][] altitudes) {
        validateAltitudeGrid(altitudes);

        int total = 0;
        int lowestAltitude = Integer.MAX_VALUE;
        int highestAltitude = Integer.MIN_VALUE;
        int lowestRow = 0;
        int lowestColumn = 0;
        int highestRow = 0;
        int highestColumn = 0;
        int oceanCount = 0;
        int beachCount = 0;
        int grasslandCount = 0;
        int forestCount = 0;
        int mountainCount = 0;

        for (int row = 0; row < TerrainGenerator.GRID_SIZE; row++) {
            for (int column = 0; column < TerrainGenerator.GRID_SIZE;
                    column++) {
                int altitude = altitudes[row][column];
                total += altitude;

                if (altitude < lowestAltitude) {
                    lowestAltitude = altitude;
                    lowestRow = row;
                    lowestColumn = column;
                }

                if (altitude > highestAltitude) {
                    highestAltitude = altitude;
                    highestRow = row;
                    highestColumn = column;
                }

                switch (BiomeMapper.classify(altitude)) {
                    case OCEAN:
                        oceanCount++;
                        break;
                    case BEACH:
                        beachCount++;
                        break;
                    case GRASSLAND:
                        grasslandCount++;
                        break;
                    case FOREST:
                        forestCount++;
                        break;
                    case MOUNTAIN:
                        mountainCount++;
                        break;
                    default:
                        throw new IllegalStateException("Unknown biome.");
                }
            }
        }

        int cellCount = TerrainGenerator.GRID_SIZE * TerrainGenerator.GRID_SIZE;

        return new TerrainStatistics(
                (double) total / cellCount,
                lowestAltitude, lowestRow, lowestColumn,
                highestAltitude, highestRow, highestColumn,
                oceanCount, beachCount, grasslandCount,
                forestCount, mountainCount);
    }
    
    private static void validateAltitudeGrid(int[][] altitudes) {
        if (altitudes == null
                || altitudes.length != TerrainGenerator.GRID_SIZE) {
            throw new IllegalArgumentException(
                    "Terrain data must contain "
                    + TerrainGenerator.GRID_SIZE + " rows.");
        }

        for (int row = 0; row < TerrainGenerator.GRID_SIZE; row++) {
            if (altitudes[row] == null
                    || altitudes[row].length != TerrainGenerator.GRID_SIZE) {
                throw new IllegalArgumentException(
                        "Each terrain row must contain "
                        + TerrainGenerator.GRID_SIZE + " columns.");
            }

            for (int column = 0; column < TerrainGenerator.GRID_SIZE;
                    column++) {
                int altitude = altitudes[row][column];

                if (altitude < 0 || altitude > 100) {
                    throw new IllegalArgumentException(
                            "Altitude values must be between 0 and 100.");
                }
            }
        }
    }
    
    public double getAverageAltitude() {
        return averageAltitude;
    }

    public int getLowestAltitude() {
        return lowestAltitude;
    }

    public int getLowestRow() {
        return lowestRow;
    }

    public int getLowestColumn() {
        return lowestColumn;
    }

    public int getHighestAltitude() {
        return highestAltitude;
    }

    public int getHighestRow() {
        return highestRow;
    }

    public int getHighestColumn() {
        return highestColumn;
    }
    
    public int getOceanCount() {
        return oceanCount;
    }
    
    public int getBeachCount() {
        return beachCount;
    }
    
    public int getGrasslandCount() {
        return grasslandCount;
    }
    
    public int getForestCount() {
        return forestCount;
    }
    
    public int getMountainCount() {
        return mountainCount;
    }
}

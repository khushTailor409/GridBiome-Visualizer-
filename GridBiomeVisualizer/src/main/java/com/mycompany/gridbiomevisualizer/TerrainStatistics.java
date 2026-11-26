package com.mycompany.gridbiomevisualizer;

public final class TerrainStatistics {
    private final double averageAltitude;
    private final int lowestAltitude;
    private final int lowestRow;
    private final int lowestColumn;
    private final int highestAltitude;
    private final int highestRow;
    private final int highestColumn;

    private TerrainStatistics(double averageAltitude,
            int lowestAltitude, int lowestRow, int lowestColumn,
            int highestAltitude, int highestRow, int highestColumn) {
        this.averageAltitude = averageAltitude;
        this.lowestAltitude = lowestAltitude;
        this.lowestRow = lowestRow;
        this.lowestColumn = lowestColumn;
        this.highestAltitude = highestAltitude;
        this.highestRow = highestRow;
        this.highestColumn = highestColumn;
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
            }
        }

        int cellCount = TerrainGenerator.GRID_SIZE
                * TerrainGenerator.GRID_SIZE;

        return new TerrainStatistics(
                (double) total / cellCount,
                lowestAltitude, lowestRow, lowestColumn,
                highestAltitude, highestRow, highestColumn);
    }
}

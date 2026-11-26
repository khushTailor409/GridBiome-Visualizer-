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
}

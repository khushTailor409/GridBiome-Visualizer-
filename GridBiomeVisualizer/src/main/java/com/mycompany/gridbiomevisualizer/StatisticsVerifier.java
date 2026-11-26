package com.mycompany.gridbiomevisualizer;

public final class StatisticsVerifier {
    private StatisticsVerifier() {
    }

    public static void main(String[] args) {
        int[][] altitudes = new int[TerrainGenerator.GRID_SIZE]
                [TerrainGenerator.GRID_SIZE];

        altitudes[0][0] = 100;

        TerrainStatistics statistics = TerrainStatistics.calculate(altitudes);

        check(statistics.getAverageAltitude() == 1.0,
                "Expected average altitude of 1.0.");
        check(statistics.getHighestAltitude() == 100,
                "Expected highest altitude of 100.");
        check(statistics.getHighestRow() == 0
                && statistics.getHighestColumn() == 0,
                "Expected highest cell at row 0, column 0.");
        check(statistics.getLowestAltitude() == 0,
                "Expected lowest altitude of 0.");
        check(statistics.getLowestRow() == 0
                && statistics.getLowestColumn() == 1,
                "Expected first lowest cell at row 0, column 1.");
        check(statistics.getOceanCount() == 99,
                "Expected 99 Ocean cells.");
        check(statistics.getMountainCount() == 1,
                "Expected 1 Mountain cell.");
        check(statistics.getBeachCount() == 0
                && statistics.getGrasslandCount() == 0
                && statistics.getForestCount() == 0,
                "Expected no Beach, Grassland, or Forest cells.");

        System.out.println("Terrain statistics verification passed.");
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new IllegalStateException(message);
        }
    }
}
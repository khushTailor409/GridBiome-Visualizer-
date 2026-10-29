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
public class TerrainGenerator {

    public static final int GRID_SIZE = 10;
    private static final int SMOOTHING_PASSES = 5;
    private final Random random;

    public TerrainGenerator() {
        this(new Random());
    }

    public TerrainGenerator(Random random) {
        this.random = random;
    }

    public int[][] generate() {
        int[][] altitudes = new int[GRID_SIZE][GRID_SIZE];

        // Generate larger terrain regions
        int[][] regions = new int[4][3];

        for (int row = 0; row < regions.length; row++) {
            for (int col = 0; col < regions[row].length; col++) {
                regions[row][col] = random.nextInt(101);
            }
        }

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int regionRow = Math.min(row / 3, regions.length - 1);
                int regionCol = Math.min(col / 4, regions[0].length - 1);

                altitudes[row][col] = regions[regionRow][regionCol]
                        + random.nextInt(11) - 5;

                altitudes[row][col] = Math.max(0,
                        Math.min(100, altitudes[row][col]));
            }
        }

        for (int pass = 0; pass < SMOOTHING_PASSES; pass++) {
            altitudes = smooth(altitudes);
        }

        return altitudes;
    }

    int[][] smooth(int[][] source) {
        int[][] result = new int[GRID_SIZE][GRID_SIZE];
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                int sum = source[row][col] * 4;
                int weight = 4;

                for (int[] direction : directions) {
                    int neighborRow = row + direction[0];
                    int neighborCol = col + direction[1];

                    if (neighborRow >= 0 && neighborRow < GRID_SIZE
                            && neighborCol >= 0 && neighborCol < GRID_SIZE) {
                        sum += source[neighborRow][neighborCol];
                        weight++;
                    }
                }

                int altitude = (int) Math.round((double) sum / weight);
                result[row][col] = Math.max(0, Math.min(100, altitude));
            }
        }

        return result;
    }
}
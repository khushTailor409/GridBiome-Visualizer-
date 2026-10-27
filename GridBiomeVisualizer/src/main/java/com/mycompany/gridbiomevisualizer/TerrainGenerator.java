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
    private static final int SMOOTHING_PASSES = 3;
    private final Random random;

    public TerrainGenerator() {
        this(new Random());
    }
    
    public TerrainGenerator(Random random) {
        this.random = random;
    }
    
    
}

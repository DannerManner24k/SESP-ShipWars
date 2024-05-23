package dk.sdu.sesp.geight.common.map;


import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.services.ICollidable;
import dk.sdu.sesp.geight.common.services.collision.ITerrain;

public class Map extends Entity implements ITerrain {
    private double[] heights;  // Array of heights for the map
    private double[] coefficients;  // Array to store polynomial coefficients

    public Map(double[] coefficients) {
        this.coefficients = coefficients;  // Store the entire array of coefficients
    }

    public Map() {
        // Initialize heights or any other necessary fields
    }



    public void setHeights(double[] heights) {
        this.heights = heights;
    }

    public double[] getCoefficients() {
        return coefficients;
    }

    @Override
    public void onCollision(GameData gameData, World world, ICollidable other) {
        System.out.println("Jaaaa map");
    }

    @Override
    public void createCrater(int x, int radius) {
        /*
        for (int i = x - radius; i <= x + radius; i++) {
            if (i >= 0 && i < heights.length) {
                heights[i] -= radius - Math.abs(i - x);
            }
        }
         */
        System.out.println("Creating crater at x: " + x + " with radius: " + radius);
    }

    @Override
    public double[] getHeights() {
        return heights;
    }
}

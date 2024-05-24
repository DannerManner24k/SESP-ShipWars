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
    }

    @Override
    public void createCrater(int centerX, int radiusIn) {
        double radius = radiusIn*3;
        int start = Math.max(0, (int)Math.floor(centerX - radius));
        int end = Math.min(heights.length, (int)Math.ceil(centerX + radius + 1));

        for (int i = start; i < end; i++) {
            double distance = Math.abs(i - centerX);
            if (distance <= radius) {
                double reductionAmount = calculateReduction(distance, radius);
                heights[i] = Math.max(0, heights[i] - reductionAmount);
            }
        }
    }

    private double calculateReduction(double distance, double radius) {
        return radius - distance;
    }

    @Override
    public double[] getHeights() {
        return heights;
    }
}

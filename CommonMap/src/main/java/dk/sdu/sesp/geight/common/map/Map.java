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
        int radius = radiusIn*2;
        System.out.println("Jaaaa map");
        System.out.println("Creating crater at x: " + centerX + " with radius: " + radius);
        for (int x = -radius; x <= radius; x++) {
            int craterX = centerX + x;
            if (craterX >= 0 && craterX < heights.length) {
                // Calculate the y depth adjustment based on the distance from the center of the crater
                double distance = Math.abs(x); // Only considering horizontal distance for 1D height array
                if (distance <= radius) {
                    // Determine the height reduction using a parabolic curve for smoothness
                    double depthReduction = (radius * radius - distance * distance) / (radius * radius) * (radius * 0.2);
                    heights[craterX] -= depthReduction; // Apply the reduction
                }
            }
        }
    }

    @Override
    public double[] getHeights() {
        return heights;
    }
}

package dk.sdu.sesp.geight.common.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.common.data.Entity;

public class Map extends Entity {
    private double[] heights;  // Array of heights for the map
    private double[] coefficients;  // Array to store polynomial coefficients

    public Map(double[] coefficients) {
        this.coefficients = coefficients;  // Store the entire array of coefficients
    }

    public double[] getHeights() {
        return heights;
    }

    public void setHeights(double[] heights) {
        this.heights = heights;
    }

    public double[] getCoefficients() {
        return coefficients;
    }
}

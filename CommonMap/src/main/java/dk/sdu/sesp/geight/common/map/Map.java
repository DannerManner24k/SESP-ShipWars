package dk.sdu.sesp.geight.common.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.common.data.Entity;

public class Map extends Entity {
    private double[] heights;  // Array of heights for the map
    private double a, b, c;    // Coefficients for the polynomial

    public Map(double a, double b, double c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public double[] getHeights() {
        return heights;
    }

    public void setHeights(double[] heights) {
        this.heights = heights;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }
}

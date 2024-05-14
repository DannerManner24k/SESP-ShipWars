package dk.sdu.sesp.geight.common.map;

import dk.sdu.sesp.geight.common.data.Entity;

public class Water extends Entity {
    private double[] heights;

    public Water(double[] heights) {
        this.heights = heights;
    }

    public double[] getHeights() {
        return heights;
    }

    public void setHeights(double[] heights) {
        this.heights = heights;
    }
}

package dk.sdu.sesp.geight.common.services.collision;

import dk.sdu.sesp.geight.common.services.ICollidable;

public interface ITerrain extends ICollidable {
    void createCrater(int x, int radius);
    double[] getHeights();
}

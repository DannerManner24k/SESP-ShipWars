package dk.sdu.sesp.geight.common.data.entityparts;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;

public class CanonPart implements EntityPart{
    protected float[] shapeCanonX;
    protected float[] shapeCanonY;
    protected float radianCanon;

    public float[] getShapeCanonX() {
        return shapeCanonX;
    }

    public void setShapeCanonX(float[] shapeCanonX) {
        this.shapeCanonX = shapeCanonX;
    }

    public float[] getShapeCanonY() {
        return shapeCanonY;
    }

    public void setShapeCanonY(float[] shapeCanonY) {
        this.shapeCanonY = shapeCanonY;
    }

    public float getRadianCanon() {
        return radianCanon;
    }

    public void setRadianCanon(float radianCanon) {
        this.radianCanon = radianCanon;
    }


    @Override
    public void process(GameData gameData, Entity entity) {
    }
}

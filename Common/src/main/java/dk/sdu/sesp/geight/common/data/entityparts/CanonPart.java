package dk.sdu.sesp.geight.common.data.entityparts;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;

public class CanonPart implements EntityPart{

    protected float[] shapeX;
    protected float[] shapeY;
    protected float x;
    protected float y;
    protected float radian;

    public CanonPart(float x, float y, float radiansCanon) {
        this.radian = radiansCanon;
        this.x = x;
        this.y = y;

    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float[] getShapeX() {
        return shapeX;
    }

    public void setShapeX(float[] shapeX) {
        this.shapeX = shapeX;
    }

    public float[] getShapeY() {
        return shapeY;
    }

    public void setShapeY(float[] shapeY) {
        this.shapeY = shapeY;
    }

    public float getRadian() {
        return radian;
    }

    public void setRadian(float radian) {
        //System.out.println("Canon radian updated to: " + radian);
        this.radian = radian;
    }


    @Override
    public void process(GameData gameData, Entity entity) {
    }
}
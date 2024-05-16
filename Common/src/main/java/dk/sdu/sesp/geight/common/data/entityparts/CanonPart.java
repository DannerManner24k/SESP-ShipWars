package dk.sdu.sesp.geight.common.data.entityparts;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;

public class CanonPart implements EntityPart{

    protected float[] shapeX;
    protected float[] shapeY;
    protected float x;
    protected float y;
    protected float radian;

    protected float[] currentShotX;
    protected float[] currentShotY;

    protected float[] lastShotX;
    protected float[] lastShotY;


    public float[] getCurrentShotX() {
        return currentShotX;
    }
    public float getCurrentShotX(int index) {
        return currentShotX[index];
    }

    public void setCurrentShotX(float[] currentShotX) {
        this.currentShotX = currentShotX;
    }

    public float[] getCurrentShotY() {
        return currentShotY;
    }

    public float getCurrentShotY(int index) {
        return currentShotY[index];
    }

    public void setCurrentShotY(float[] currentShotY) {
        this.currentShotY = currentShotY;
    }

    public float[] getLastShotX() {
        return lastShotX;
    }

    public float getLastShotX(int index) {
        return lastShotX[index];
    }

    public void setLastShotX(float[] lastShotX) {
        this.lastShotX = lastShotX;
    }

    public float[] getLastShotY() {
        return lastShotY;
    }

    public float getLastShotY(int index) {
        return lastShotY[index];
    }

    public void setLastShotY(float[] lastShotY) {
        this.lastShotY = lastShotY;
    }

    public CanonPart(float x, float y, float radiansCanon) {
        this.radian = radiansCanon;
        this.x = x;
        this.y = y;
        this.currentShotX = new float[2];
        this.currentShotY = new float[2];
        this.lastShotX = new float[2];
        this.lastShotY = new float[2];
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

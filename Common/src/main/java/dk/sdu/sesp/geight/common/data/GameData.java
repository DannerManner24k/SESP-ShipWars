package dk.sdu.sesp.geight.common.data;

public class GameData {
    private float delta;
    private int displayWidth;
    private int displayHeight;
    private long lastPlayerFireTime = 0;

    private final GameKeys keys = new GameKeys();

    public GameKeys getKeys() {
        return keys;
    }

    public float getDelta() {
        return delta;
    }

    public void setDelta(float delta) {
        this.delta = delta;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public long getLastPlayerFireTime() {
        return lastPlayerFireTime;
    }

    public void setLastPlayerFireTime(long lastPlayerFireTime) {
        this.lastPlayerFireTime = lastPlayerFireTime;
    }

}

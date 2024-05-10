package dk.sdu.sesp.geight.common.data;

public class GameData {
    private int displayWidth  = 800 ;
    private int displayHeight = 800;
    //private final GameKeys keys = new GameKeys();
    private long lastUpdateTime;


    /*public GameKeys getKeys() {
        return keys;
    }
    public float getDelta() {
        long currentTime = System.nanoTime();
        float delta = (currentTime - lastUpdateTime) / 1_000_000_000.0f;  // Convert nanoseconds to seconds
        lastUpdateTime = currentTime;  // Update lastUpdateTime to the current time
        return delta;
    }*/

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


}

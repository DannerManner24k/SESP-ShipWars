package dk.sdu.sesp.geight.common.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Map {
    private Texture mapBackground;
    private int height;
    private int width;

    public Map() {
        // Load the background texture (make sure the path is relative to the assets' directory)
        mapBackground = new Texture("CommonMap/assets/MapBackground.png");

        // Initialize your map's width and height
        this.width = mapBackground.getWidth();
        this.height = mapBackground.getHeight();
    }

    public void render(SpriteBatch batch, int screenWidth, int screenHeight) {
        // Calculate the horizontal and vertical offsets to center the scaled image on the screen
        float offsetX = 0;
        float offsetY = 0;

        // Draw the background texture
        batch.draw(mapBackground, offsetX, offsetY);
    }

    public void dispose() {
        // Dispose of the background texture when it's no longer needed
        mapBackground.dispose();
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}

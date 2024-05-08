package dk.sdu.sesp.geight.common.map;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.common.data.Entity;

public class Map extends Entity {
    private Texture mapBackground;

    public Map() {
        super(0, 0, 0, 0);
        // Load the background texture (make sure the path is relative to the assets' directory)
        mapBackground = new Texture("CommonMap/assets/MapBackground.png");

        // Initialize your map's width and height
        super.width = mapBackground.getWidth();
        super.height = mapBackground.getHeight();
    }

    @Override
    public void render(SpriteBatch batch) {
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

}

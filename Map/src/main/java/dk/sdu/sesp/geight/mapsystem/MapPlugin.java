package dk.sdu.sesp.geight.mapsystem;

import dk.sdu.sesp.geight.common.map.Map;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapPlugin {
    private Map map;

    public MapPlugin() {
        // Initialize the standard map
        map = new Map();
    }

    public void render(SpriteBatch batch, int  screenWidth, int screenHeight) {
        // Render the standard map
        map.render(batch, screenWidth, screenHeight);
    }

    public void dispose() {
        // Dispose of resources
        map.dispose();
    }
}

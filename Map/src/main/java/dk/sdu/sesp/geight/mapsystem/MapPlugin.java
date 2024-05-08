package dk.sdu.sesp.geight.mapsystem;

import dk.sdu.sesp.geight.common.map.Map;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapPlugin {
    private Map map;

    public MapPlugin() {
        // Initialize the standard map
        map = new Map();
    }

    public void render(SpriteBatch batch) {
        // Render the standard map
        map.render(batch);
    }

    public void dispose() {
        // Dispose of resources
        map.dispose();
    }
}

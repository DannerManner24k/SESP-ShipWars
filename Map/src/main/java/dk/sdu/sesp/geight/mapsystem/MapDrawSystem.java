package dk.sdu.sesp.geight.mapsystem;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.map.Map;
import dk.sdu.sesp.geight.common.map.Water;
import dk.sdu.sesp.geight.common.services.IDrawService;

public class MapDrawSystem implements IDrawService {
    @Override
    public void draw(ShapeRenderer sr, World world) {
        for (Entity entity : world.getEntities(Water.class)) {
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(Color.BLUE);
            double[] waterHeight = ((Water) entity).getHeights();
            for (int x = 1; x < waterHeight.length; x++) {
                float baseY = 0; // Assuming the bottom of the screen or base of the terrain
                sr.triangle((float) x - 1, (float) waterHeight[x - 1], (float) x, (float) waterHeight[x], (float) x - 1, baseY);
                sr.triangle((float) x, (float) waterHeight[x], (float) x, baseY, (float) x - 1, baseY);
            }
            sr.end();
        }

        for (Entity entity : world.getEntities(Map.class)) {
            sr.begin(ShapeRenderer.ShapeType.Filled); // Use filled type for filling areas
            sr.setColor(Color.BROWN); // Set to a suitable ground color
            double[] heights = ((Map) entity).getHeights();
            for (int x = 1; x < heights.length; x++) {
                float baseY = 0; // Assuming the bottom of the screen or base of the terrain
                sr.triangle((float) x - 1, (float) heights[x - 1], (float) x, (float) heights[x], (float) x - 1, baseY);
                sr.triangle((float) x, (float) heights[x], (float) x, baseY, (float) x - 1, baseY);
            }
            sr.end();
        }
    }

    @Override
    public int getPriority() {
        return 1;
    }
}

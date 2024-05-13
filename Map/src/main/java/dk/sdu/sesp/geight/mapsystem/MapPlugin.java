package dk.sdu.sesp.geight.mapsystem;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.map.Map;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.common.services.IGamePluginService;

public class MapPlugin implements IGamePluginService {
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera staticCamera;
    private Entity map;

    @Override
    public void start(GameData gameData, World world, SpriteBatch batch) {
        this.shapeRenderer = new ShapeRenderer();
        this.map = createMap(gameData, world);  // Create the map when the plugin starts
    }

    private Entity createMap(GameData gameData, World world) {
        // Define points through which the polynomial should pass
        double[][] points = {
                {0, 100}, // Start at bottom
                {200, 500}, // Peak
                {400, 300}, // Valley
                {600, 500}, // Peak
                {800, 100} // End at bottom
        };

        double[] coefficients = PolynomialInterpolator.interpolate(points);
        Map map = new Map(coefficients);
        generateMap(gameData, map, gameData.getDisplayWidth());
        world.addEntity(map);
        return map;
    }

    private void generateMap(GameData gameData, Map map, int width) {
        double[] coefficients = map.getCoefficients();
        double[] heights = new double[width];
        for (int x = 0; x < width; x++) {
            double height = 0;
            for (int d = 0; d < coefficients.length; d++) {
                height += coefficients[d] * Math.pow(x, coefficients.length - 1 - d);
            }
            heights[x] = height;
        }
        map.setHeights(heights);
    }

    @Override
    public void stop(GameData gameData, World world, SpriteBatch batch) {
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
            shapeRenderer = null;
        }
        if (map != null) {
            world.removeEntity(map);
            map = null;
        }
    }
}

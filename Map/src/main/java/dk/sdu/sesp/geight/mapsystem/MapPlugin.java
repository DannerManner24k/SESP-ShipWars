package dk.sdu.sesp.geight.mapsystem;

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
        System.out.println("MapPlugin starting");
        this.shapeRenderer = new ShapeRenderer();
        this.map = createMap(gameData, world);
    }

    private Entity createMap(GameData gameData, World world) {
        Map map = new Map(1.0, -0.01, 100);  // Example coefficients for the polynomial
        generateMap(map, gameData.getDisplayWidth());
        renderMap(gameData, world);
        world.addEntity(map);
        return map;
    }

    private void generateMap(Map map, int width) {
        double[] heights = new double[width];
        double a = map.getA();
        double b = map.getB();
        double c = map.getC();
        for (int x = 0; x < width; x++) {
            heights[x] = a * Math.pow(x, 2) + b * x + c;
        }
        map.setHeights(heights);
    }

    public void renderMap(GameData gameData, World world) {
        if (shapeRenderer != null) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.WHITE);
            double[] heights = ((Map)map).getHeights();
            for (int x = 1; x < heights.length; x++) {
                shapeRenderer.line(x - 1, (float)heights[x - 1], x, (float)heights[x]);
            }
            shapeRenderer.end();
        }
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

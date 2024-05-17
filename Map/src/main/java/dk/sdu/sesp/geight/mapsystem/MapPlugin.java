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
import dk.sdu.sesp.geight.common.map.Water;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.common.map.Water;
import dk.sdu.sesp.geight.common.services.IGamePluginService;

import java.util.Arrays;
import java.util.Random;


public class MapPlugin implements IGamePluginService {
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera staticCamera;
    private Entity map;
    private Entity water;
    private Color mountainColor = Color.BROWN;

    private static final int NUM_POINTS = 5; // Number of points to generate
    private static final int MIN_X = 0; // Minimum x coordinate
    private static final int MAX_X = 800; // Maximum x coordinate
    private static final int MIN_Y = 100; // Minimum y coordinate
    private static final int MAX_Y = 500; // Maximum y coordinate

    @Override
    public void start(GameData gameData, World world, SpriteBatch batch) {
        this.shapeRenderer = new ShapeRenderer();
        this.map = createMap(gameData, world);  // Create the map when the plugin starts
        this.water = createWater(gameData, world); // Create the water
    }

    private double[][] generateRandomPoints(){
        double[][] points = new double[NUM_POINTS][2];
        Random random = new Random();

        for(int i = 0; i < NUM_POINTS; i++){
            double x = random.nextInt(MAX_X - MIN_X + 1) + MIN_X;
            double y = random.nextInt(MAX_Y - MIN_Y + 1) + MIN_Y;
            points[i][0] = x;
            points[i][1] = y;
        }
        return points;
    }

    private Entity createMap(GameData gameData, World world) {
        double[][] points = generateRandomPoints();

        // Convert points to the format used in createMap method
        double[] coefficients = PolynomialInterpolator.interpolate(points);
        Map map = new Map(coefficients);
        generateMap(gameData, map, gameData.getDisplayWidth());
        world.addEntity(map);
        return map;
    }

    private Entity createWater(GameData gameData, World world){
        //Define the water line
        double waterLevel = 300;
        double[] waterHeights = new double[gameData.getDisplayWidth()];
        /*Arrays.fill(waterHeights, waterLevel);
         */

        Water waterMap = new Water(waterHeights);
        generateWater(gameData, waterMap, gameData.getDisplayWidth(), waterLevel);
        world.addEntity(waterMap);
        return waterMap;
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

    private void generateWater(GameData gameData, Water water, int width, double waterLevel) {
        double[] heights = water.getHeights();
        for (int x = 0; x < width; x++) {
            if (heights[x] < waterLevel) {
                heights[x] = waterLevel;
            }
        }
        water.setHeights(heights);
    }


    /*public void render(GameData gameData, World world, SpriteBatch batch){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        //render mountains
        shapeRenderer.setColor(mountainColor);
        for(Entity entity : world.getEntities(Map.class)) {
            Map map = (Map) entity;
            double[] heights = map.getHeights();
            for (int x = 0; x < heights.length - 1; x++){
                shapeRenderer.rect(x, 0, 1, (float) heights[x]);
            }
        }
        shapeRenderer.end();
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
        if (water != null) {
            world.removeEntity(water);
            water = null;
        }
    }
}

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

import java.util.*;


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
        List<Double> xCoordinates = findWaterTallerThanMountain((Map) map, (Water) water);
        System.out.println("X-coordinates where water is taller than the mountain: " + xCoordinates);
    }

    private double[][] generateRandomPoints(){
        double[][] points = new double[NUM_POINTS][2];
        Random random = new Random();
        int maxHeight = 500;
        int numSections = 5;
        int sectionWidth = MAX_X / numSections;


        /*for(int i = 0; i < NUM_POINTS; i++){
            double x = random.nextInt(MAX_X - MIN_X + 1) + MIN_X;
            double y = random.nextInt(maxHeight - MIN_Y + 1) + MIN_Y;
            points[i][0] = x;
            points[i][1] = y;
        }*/
        for (int sectionIndex = 0; sectionIndex < numSections; sectionIndex++) {
            // Calculate the range of x-values for the current section
            int startX = sectionIndex * sectionWidth;
            int endX = (sectionIndex + 1) * sectionWidth - 1;

            // Generate points within the current section
            for (int i = sectionIndex * (NUM_POINTS / numSections); i < (sectionIndex + 1) * (NUM_POINTS / numSections); i++) {
                double x = random.nextInt(endX - startX + 1) + startX;
                double y = random.nextInt(maxHeight - MIN_Y + 1) + MIN_Y;
                if (i == 0) { // Ensure first y-coordinate is lower than the next y-coordinate
                    y = random.nextInt(maxHeight / 2 - MIN_Y) + MIN_Y;
                } else if (i == NUM_POINTS - 1) { // Ensure last y-coordinate is lower than the second last y-coordinate
                    y = random.nextInt((int)points[i-1][1] - MIN_Y) + MIN_Y;
                } else {
                    y = random.nextInt(maxHeight - MIN_Y + 1) + MIN_Y;
                }
                points[i][0] = x;
                points[i][1] = y;
            }
        }
        // Sort the points based on their x-coordinates
        Arrays.sort(points, Comparator.comparingDouble(a -> a[0]));

        // Ensure the y-coordinate doesn't exceed maxHeight
        for (int i = 0; i < NUM_POINTS; i++) {
            points[i][1] = Math.min(points[i][1], maxHeight);
        }

        // Print the generated points
        for (int i = 0; i < points.length; i++) {
            System.out.println(points[i][0] + ", " + points[i][1]);
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
        Random random = new Random();
        double waterLevel = 300;

        int lowestPointX = random.nextInt(width / 2) + width / 4; // Ensure the lowest point is not too close to the edges
        double lowestPointHeight = waterLevel - random.nextDouble() * 50;

        for (int x = 0; x < width; x++) {
            double height = 0;
            for (int d = 0; d < coefficients.length; d++) {
                height += coefficients[d] * Math.pow(x, coefficients.length - 1 - d);
            }
            if (x == lowestPointX) {
                height = Math.min(height, lowestPointHeight);
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
    }*/
    private List<Double> findWaterTallerThanMountain(Map map, Water water) {
        List<Double> xCoordinates = new ArrayList<>();
        double[] mountainHeights = map.getHeights();
        double[] waterHeights = water.getHeights();
        int width = Math.min(mountainHeights.length, waterHeights.length);

        for (int x = 0; x < width; x++) {
            double mountainHeight = mountainHeights[x];
            double waterHeight = waterHeights[x];

            if (waterHeight > mountainHeight) {
                xCoordinates.add((double)x); // Add the x-coordinate where water is taller than the mountain
            }
        }
        return xCoordinates;
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

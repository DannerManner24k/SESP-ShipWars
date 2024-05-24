package dk.sdu.sesp.geight.mapsystem;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.map.Map;
import dk.sdu.sesp.geight.common.map.Water;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.common.services.IGamePluginService;

import java.util.*;

public class MapPlugin implements IGamePluginService {
    private ShapeRenderer shapeRenderer;
    private OrthographicCamera staticCamera;
    private Entity map;
    private Entity water;
    private Color mountainColor = Color.BROWN;
    private GameData gameData;

    private static final int MIN_X = 0; // Minimum x coordinate
    private static int MAX_X; // Maximum x coordinate
    private static final int MIN_Y = 100; // Minimum y coordinate
    private static final int MAX_Y = 350; // Maximum y coordinate
    private static final double WATER_LEVEL = 150; // Water level
    private static final int POOL_WIDTH = 100; // Minimum width of the pools
    private static final int POOL_DEPTH = 100; // Minimum depth of the pools
    private static final int NUM_EXTREMA = 8; // Number of extrema points
    private static final int POOL_REGION_WIDTH = 200; // Width of the region for each pool

    @Override
    public int getPriority() {
        return 0;
    }

    @Override
    public void start(GameData gameData, World world, SpriteBatch batch) {
        this.gameData = gameData;
        MAX_X = gameData.getDisplayWidth(); // Maximum x coordinate
        this.shapeRenderer = new ShapeRenderer();
        this.map = createMap(gameData, world);  // Create the map when the plugin starts
        this.water = createWater(gameData, world); // Create the water
        List<Double> xCoordinates = findWaterTallerThanMountain((Map) map, (Water) water);
        System.out.println("X-coordinates where water is taller than the mountain: " + xCoordinates);
        List<Double> spawnPointsX1 = new ArrayList<>();
        List<Double> spawnPointsX2 = new ArrayList<>();
        sortCoordinates(xCoordinates, spawnPointsX1, spawnPointsX2);
        System.out.println("Spawn points 1: " + spawnPointsX1);
        System.out.println("Spawn points 2: " + spawnPointsX2);
        world.setSpawnPoints(spawnPointsX1, spawnPointsX2);
    }

    private void sortCoordinates(List<Double> xCoordinates, List<Double> spawnPointsX1, List<Double> spawnPointsX2) {
        if (xCoordinates == null || xCoordinates.isEmpty()) {
            return;
        }

        double start = xCoordinates.get(0);
        double end = start;

        for (int i = 1; i < xCoordinates.size(); i++) {
            if (xCoordinates.get(i) == xCoordinates.get(i - 1) + 1.0) {
                end = xCoordinates.get(i);
            } else {
                spawnPointsX1.add(start);
                spawnPointsX2.add(end);
                start = xCoordinates.get(i);
                end = start;
            }
        }

        // Add the last range
        spawnPointsX1.add(start);
        spawnPointsX2.add(end);
    }


    private double[][] generateExtremaPoints(int numExtrema, int minX, int maxX, int minY, int maxY, double waterLevel, int poolWidth, int poolDepth) {
        double[][] extremaPoints = new double[numExtrema][2];
        Random random = new Random();

        // Ensure first and last points are high
        extremaPoints[0][0] = minX;
        extremaPoints[0][1] = maxY;
        extremaPoints[numExtrema - 1][0] = maxX;
        extremaPoints[numExtrema - 1][1] = maxY;

        // Calculate the indices for the pool regions
        int poolRegionUnits = POOL_REGION_WIDTH / (maxX / numExtrema);
        int poolWidthUnits = poolWidth / (maxX / numExtrema);

        // Create the first low point pool in the first 200 units
        int firstLowPointStartIndex = random.nextInt(poolRegionUnits - poolWidthUnits) + 1;
        int firstLowPointEndIndex = firstLowPointStartIndex + poolWidthUnits;

        // Create the second low point pool in the last 200 units
        int secondLowPointStartIndex = numExtrema - 2 - random.nextInt(poolRegionUnits - poolWidthUnits);
        int secondLowPointEndIndex = secondLowPointStartIndex + poolWidthUnits;

        // Set low points for the first pool
        for (int i = firstLowPointStartIndex; i <= firstLowPointEndIndex; i++) {
            extremaPoints[i][0] = minX + (maxX - minX) * i / (numExtrema - 1);
            extremaPoints[i][1] = waterLevel - poolDepth + random.nextDouble() * poolDepth;
        }

        // Set low points for the second pool
        for (int i = secondLowPointStartIndex; i <= secondLowPointEndIndex; i++) {
            extremaPoints[i][0] = minX + (maxX - minX) * i / (numExtrema - 1);
            extremaPoints[i][1] = waterLevel - poolDepth + random.nextDouble() * poolDepth;
        }

        // Ensure mountains in the middle
        int mountainStartIndex = firstLowPointEndIndex + 1;
        int mountainEndIndex = secondLowPointStartIndex - 1;
        for (int i = mountainStartIndex; i <= mountainEndIndex; i++) {
            extremaPoints[i][0] = minX + (maxX - minX) * i / (numExtrema - 1);
            extremaPoints[i][1] = maxY - random.nextDouble() * (maxY - waterLevel);
        }

        // Generate the rest of the points
        for (int i = 1; i < numExtrema - 1; i++) {
            if ((i < firstLowPointStartIndex || i > firstLowPointEndIndex) &&
                    (i < secondLowPointStartIndex || i > secondLowPointEndIndex) &&
                    (i < mountainStartIndex || i > mountainEndIndex)) {
                extremaPoints[i][0] = minX + (maxX - minX) * i / (numExtrema - 1);
                extremaPoints[i][1] = minY + random.nextDouble() * (maxY - minY);
            }
        }

        return extremaPoints;
    }

    private Entity createMap(GameData gameData, World world) {
        double[][] extremaPoints = generateExtremaPoints(NUM_EXTREMA, MIN_X, MAX_X, MIN_Y, MAX_Y, WATER_LEVEL, POOL_WIDTH, POOL_DEPTH);

        // Convert points to the format used in createMap method
        double[] coefficients = PolynomialInterpolator.interpolate(extremaPoints);
        Map map = new Map(coefficients);
        generateMap(gameData, map, gameData.getDisplayWidth());
        world.addEntity(map);

        return map;
    }

    private Entity createWater(GameData gameData, World world) {
        double[] waterHeights = new double[gameData.getDisplayWidth()];

        Water waterMap = new Water(waterHeights);
        generateWater(gameData, waterMap, gameData.getDisplayWidth(), WATER_LEVEL);
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

    private List<Double> findWaterTallerThanMountain(Map map, Water water) {
        List<Double> xCoordinates = new ArrayList<>();
        double[] mountainHeights = map.getHeights();
        double[] waterHeights = water.getHeights();
        int width = Math.min(mountainHeights.length, waterHeights.length);

        for (int x = 0; x < width; x++) {
            double mountainHeight = mountainHeights[x];
            double waterHeight = waterHeights[x];

            if (waterHeight > mountainHeight) {
                xCoordinates.add((double) x);
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

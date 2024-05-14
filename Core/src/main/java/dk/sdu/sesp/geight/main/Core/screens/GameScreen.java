package dk.sdu.sesp.geight.main.Core.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.map.Map;
import dk.sdu.sesp.geight.common.map.Water;
import dk.sdu.sesp.geight.common.data.entityparts.CanonPart;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;
import dk.sdu.sesp.geight.common.services.IGamePluginService;
import dk.sdu.sesp.geight.common.services.IPostEntityProcessingService;
import dk.sdu.sesp.geight.enemysystem.Enemy;
import dk.sdu.sesp.geight.playersystem.Player;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.sesp.geight.main.GameEngine.GameLogic;
import dk.sdu.sesp.geight.main.managers.GameInputProcessor;
import dk.sdu.sesp.geight.main.managers.TurnManager;
import du.sdu.sesp.geight.common.bullet.Bullet;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;
import static java.util.stream.Collectors.toList;

public class GameScreen implements ApplicationListener {

    private static OrthographicCamera cam;

    private final GameData gameData = new GameData();
    private List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    private List<IPostEntityProcessingService> postEntityProcessors = new ArrayList<>();
    private World world = new World();
    private GameLogic gameLogic;
    private TurnManager turnManager;
    private SpriteBatch batch;
    private Stage stage;
    private ShapeRenderer sr;



    @Override
    public void create() {
        System.out.println("hej");
        this.batch = new SpriteBatch();// Set the batch
        this.stage = new Stage();
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());
        System.out.println(gameData.getDisplayWidth() + " " + gameData.getDisplayHeight());

        gameLogic = new GameLogic();
        turnManager = new TurnManager();

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData, turnManager)
        );
        System.out.println("Before loading entities");
        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            if (iGamePlugin != null) {
                iGamePlugin.start(gameData, world, batch);
            } else if (iGamePlugin == null) {
                System.out.println("Plugin is null");
            }
        }
        System.out.println("After loading entities");
    }

    @Override
    public void render() {

        // clear screen to black
        Gdx.gl.glClearColor(255,255,255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();

        draw();

        gameData.getKeys().update();
    }

    private void update() {
        // Update
        gameLogic.updateGame();

        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        cam.update();
        batch.setProjectionMatrix(cam.combined);
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Water) {
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
        }

        for (Entity entity : world.getEntities()) {
            if (entity instanceof Map) {
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

        // Draw entities like Enemy and Player after the map
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Enemy || entity instanceof Player) {
                sr.begin(ShapeRenderer.ShapeType.Filled);
                Color entityColor = entity instanceof Enemy ? Color.RED : Color.GREEN;
                sr.setColor(entityColor);

                float[] shapeX = entity.getShapeX();
                float[] shapeY = entity.getShapeY();

                for (int i = 1; i < shapeX.length - 1; i++) {
                    float x1 = shapeX[0], y1 = shapeY[0]; // always the first vertex
                    float x2 = shapeX[i], y2 = shapeY[i]; // current vertex
                    float x3 = shapeX[i + 1], y3 = shapeY[i + 1]; // next vertex

                    sr.triangle(x1, y1, x2, y2, x3, y3);
                }
                sr.line(shapeX[shapeX.length - 1], shapeY[shapeY.length - 1], shapeX[0], shapeY[0]);


                CanonPart canonPart = entity.getPart(CanonPart.class);
                float[] shapeCanonX = canonPart.getShapeX();
                float[] shapeCanonY = canonPart.getShapeY();

                for (int i = 1; i < shapeCanonX.length - 1; i++) {
                    float x1 = shapeCanonX[0], y1 = shapeCanonY[0]; // always the first vertex
                    float x2 = shapeCanonX[i], y2 = shapeCanonY[i]; // current vertex
                    float x3 = shapeCanonX[i + 1], y3 = shapeCanonY[i + 1]; // next vertex

                    sr.triangle(x1, y1, x2, y2, x3, y3);
                }

                sr.end();
            }
        }
        for (Entity entity : world.getEntities()){
            if (entity instanceof Bullet){
                sr.begin(ShapeRenderer.ShapeType.Filled);
                sr.setColor(Color.BLACK);
                float[] shapeX = entity.getShapeX();
                float[] shapeY = entity.getShapeY();
                for (int i = 1; i < shapeX.length - 1; i++) {
                    float x1 = shapeX[0], y1 = shapeY[0]; // always the first vertex
                    float x2 = shapeX[i], y2 = shapeY[i]; // current vertex
                    float x3 = shapeX[i + 1], y3 = shapeY[i + 1]; // next vertex

                    sr.triangle(x1, y1, x2, y2, x3, y3);
                }
                sr.end();
            }
        }
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    private Collection<? extends IGamePluginService> getPluginServices() {
        return ServiceLoader.load(IGamePluginService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return ServiceLoader.load(IEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return ServiceLoader.load(IPostEntityProcessingService.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}

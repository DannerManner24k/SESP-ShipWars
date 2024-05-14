package dk.sdu.sesp.geight.main.Core.screens;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.map.Water;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;
import dk.sdu.sesp.geight.common.services.IGamePluginService;
import dk.sdu.sesp.geight.common.services.IPostEntityProcessingService;
import dk.sdu.sesp.geight.common.map.Map;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.sesp.geight.main.GameEngine.GameLogic;
import dk.sdu.sesp.geight.main.managers.GameInputProcessor;

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

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData)
        );
        System.out.println("Before loading entities");
        // Lookup all Game Plugins using ServiceLoader
        for (IGamePluginService iGamePlugin : getPluginServices()) {
            if(iGamePlugin != null) {
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

        /*batch.begin();
        for (Entity entity : world.getEntities()) {
            if (entity.getTexture() != null) {
                batch.draw(entity.getTexture(), entity.getX(), entity.getY(), entity.getWidth(), entity.getHeight());
            }

        }
        batch.end(); // End of batch operations

         */
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Water) {
                sr.begin(ShapeRenderer.ShapeType.Filled);
                sr.setColor(Color.BLUE);
                double[] waterHeight = ((Water) entity).getHeights();
                for (int x = 1; x < waterHeight.length; x++) {
                    float baseY = 0; // Assuming the bottom of the screen or base of the terrain
                    sr.triangle((float)x - 1, (float)waterHeight[x - 1], (float)x, (float)waterHeight[x], (float)x - 1, baseY);
                    sr.triangle((float)x, (float)waterHeight[x], (float)x, baseY, (float)x - 1, baseY);
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
                    // Draw a polygon from (x-1, height[x-1]) to (x, height[x]) and down to the base
                    float baseY = 0; // Assuming the bottom of the screen or base of the terrain
                    sr.triangle((float)x - 1, (float)heights[x - 1], (float)x, (float)heights[x], (float)x - 1, baseY);
                    sr.triangle((float)x, (float)heights[x], (float)x, baseY, (float)x - 1, baseY);
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

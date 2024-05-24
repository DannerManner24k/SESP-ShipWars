package dk.sdu.sesp.geight.main.Core.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.services.IDrawService;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;
import dk.sdu.sesp.geight.common.services.IGamePluginService;
import dk.sdu.sesp.geight.common.services.IPostEntityProcessingService;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.Screen;
import dk.sdu.sesp.geight.main.GameEngine.GameLogic;
import dk.sdu.sesp.geight.main.managers.GameInputProcessor;
import dk.sdu.sesp.geight.main.managers.TurnManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class GameScreen implements Screen {
    private Game game;
    private static OrthographicCamera cam;
    private final GameData gameData = new GameData();
    private List<IEntityProcessingService> entityProcessors = new ArrayList<>();
    private List<IPostEntityProcessingService> postEntityProcessors = new ArrayList<>();
    private World world = new World();
    private GameLogic gameLogic;
    private TurnManager turnManager;
    private SpriteBatch batch;
    private Texture background;
    private Stage stage;
    private ShapeRenderer sr;

    public GameScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        this.batch = new SpriteBatch();
        this.stage = new Stage();
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        gameLogic = new GameLogic();
        turnManager = new TurnManager();

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);
        cam.update();

        background = new Texture(Gdx.files.internal("Core/assets/Background.png"));

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(
                new GameInputProcessor(gameData, turnManager)
        );

        for (IGamePluginService iGamePlugin : getPluginServices()) {
            if (iGamePlugin != null) {
                iGamePlugin.start(gameData, world, batch);
            }
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();

        gameData.setDelta(Gdx.graphics.getDeltaTime());

        update();
        draw();
        gameData.getKeys().update();

        gameOver(gameData);

    }

    private void update() {
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

        for (IDrawService drawable : getDrawServices()) {
            drawable.draw(sr, world);
        }
    }

    private void gameOver(GameData gameData) {
        if (gameData.isGameOver()){
            game.setScreen(new GameOverScreen(game));
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
    public void hide() {
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

    private Collection<? extends IDrawService> getDrawServices() {
        return ServiceLoader.load(IDrawService.class).stream().map(ServiceLoader.Provider::get).sorted((s1, s2) -> {
                    int primaryComparison = Integer.compare(s1.getPriority(), s2.getPriority());
                    return primaryComparison != 0 ? primaryComparison : s1.getPriority();
                })
                .collect(Collectors.toList());
    }
}

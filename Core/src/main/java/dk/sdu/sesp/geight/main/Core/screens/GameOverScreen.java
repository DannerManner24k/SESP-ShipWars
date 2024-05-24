package dk.sdu.sesp.geight.main.Core.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameOverScreen  implements Screen {
    private SpriteBatch batch;
    private Game game;
    private Texture img;
    private BitmapFont font;
    private BitmapFont titleFont;

    public GameOverScreen(Game game) {
        this.game = game;
        batch = new SpriteBatch();
        img = new Texture(Gdx.files.internal("Core/assets/Background.png"));
        titleFont = new BitmapFont();
        titleFont.getData().setScale(4);
        font = new BitmapFont();
        font.getData().setScale(2); // Scale the font size
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        batch.draw(img, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        titleFont.setColor(Color.RED);
        titleFont.draw(batch, "Game Over", Gdx.graphics.getWidth() / 2 - 150, Gdx.graphics.getHeight() / 2 + 100);

        font.setColor(1, 0, 0, 1);

        font.draw(batch, "Press ENTER to start the game", Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 - 100);
        font.draw(batch, "Press ESC to exit the game", Gdx.graphics.getWidth() / 2 - 200, Gdx.graphics.getHeight() / 2 - 200);
        batch.end();

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            game.setScreen(new GameScreen(game)); // Pass the Game instance
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Gdx.app.exit();
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
        batch.dispose();
        img.dispose();
        font.dispose();
    }
}


package dk.sdu.sesp.geight.main.Core.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.*;

import dk.sdu.sesp.geight.main.Core.Boot;
/*
public class StartScreen extends ScreenAdapter {
    private SpriteBatch batch;
    private Stage stage;

    public StartScreen() {
        batch = new SpriteBatch();
        stage = new Stage();
        Gdx.input.setInputProcessor(stage); // Set input processor

        // Initialize UI components
        startButton. setSize(200,200);
        startButton.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2); // Set position

        // Add event listener to the button
        startButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Change to the game screen when the button is pressed
                Boot.INSTANCE.setScreen((Screen) new GameScreen());
            }
        });

        stage.addActor(startButton); // Add button to the stage
    }

    @Override
    public void render(float delta) {
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        stage.dispose();
        skin.dispose();
    }
}

 */

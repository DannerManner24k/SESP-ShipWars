package dk.sdu.sesp.geight.playersystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dk.sdu.sesp.geight.character.Character;

public class Player extends Character {

    public Player(float x, float y) {
        super(x, y, 64, 64, "player.png", 100);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        handleInput(deltaTime);
    }

    private void handleInput(float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            position.x -= 200 * deltaTime;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            position.x += 200 * deltaTime;
        }
        // Add other input controls here
    }

    @Override
    protected void onDeath() {
        System.out.println("Player died!");
        // Handle player death
    }
}

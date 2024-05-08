package dk.sdu.sesp.geight.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.common.data.Entity;

public abstract class Character extends Entity{

    protected Texture texture;
    protected int health;

    public Character(float x, float y, float width, float height, String texturePath, int health) {
        super(x, y, width, height);
        this.texture = new Texture(texturePath);
        this.health = health;
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // Common update logic for all characters
    }

    @Override
    public void render(SpriteBatch batch) {
        batch.draw(texture, position.x, position.y, width, height);
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) {
            onDeath();
        }
    }

    protected abstract void onDeath();

    public void dispose() {
        texture.dispose();
    }
}

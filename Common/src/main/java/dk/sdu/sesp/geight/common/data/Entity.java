package dk.sdu.sesp.geight.common.data;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.UUID;

public abstract class Entity {
    private final UUID ID = UUID.randomUUID();
    protected Vector2 position;
    protected Vector2 velocity;
    protected float width;
    protected float height;
    protected float radius;
    protected Texture texture;


    public String getID() {
        return ID.toString();
    }

    public void update(float deltaTime) {
        position.add(velocity.x * deltaTime, velocity.y * deltaTime);
    }


    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        this.position.set(x, y);
    }

    public Vector2 getVelocity() {
        return velocity;
    }

    public void setVelocity(float x, float y) {
        this.velocity.set(x, y);
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public Texture getTexture() {
        return texture;
    }
    public void setTexture(Texture texture) {
        this.texture = texture;
    }
}

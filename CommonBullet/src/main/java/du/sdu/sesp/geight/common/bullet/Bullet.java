package du.sdu.sesp.geight.common.bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.common.data.Entity;

public class Bullet extends Entity {
    private int damage;
    private float speed;
    private boolean isActive;
    private Vector2D velocity;
    private static final float GRAVITY = -9.81f; // Gravity effect


    @Override
    public void update(float deltaTime) {

        float newVelocityY = getVelocityY() + GRAVITY * deltaTime; // Correct handling of gravity
        setVelocityY(newVelocityY);

        super.update(deltaTime);
    }



    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {this.damage = damage;}

    public float getSpeed() {return speed;}

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void setVelocity(Vector2D velocity) {
    }
    public Vector2D getVelocity() {return this.velocity;}


}

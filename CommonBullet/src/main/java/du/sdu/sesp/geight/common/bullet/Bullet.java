package du.sdu.sesp.geight.common.bullet;


import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;

public class Bullet extends Entity {
    private int damage;
    private float speed;
    private boolean active = true;
    private float strength;
    private float velocityX;
    private float velocityY;
    private Vector2D velocity;
    private static final float GRAVITY = -9.81f; // Gravity effect

    public void update(float deltaTime) {

        float newVelocityY = getVelocityY() + GRAVITY * deltaTime; // Correct handling of gravity
        setVelocityY(newVelocityY);

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
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public float getStrength() {
        return strength;
    }

    public void setStrength(float strength) {
        this.strength = strength;
    }

    public void setVelocity(Vector2D velocity) {
        this.velocity = velocity;
    }
    public Vector2D getVelocity() {return this.velocity;}



}

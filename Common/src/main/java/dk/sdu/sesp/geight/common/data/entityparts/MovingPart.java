/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.sesp.geight.common.data.entityparts;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;

import static java.lang.Math.*;


public class MovingPart
        implements EntityPart {

    private float dx, dy;
    private float deceleration, acceleration;
    private float maxSpeed, rotationSpeed;
    private boolean left, right, up;

    // Constructor for bullet
    public MovingPart(float deceleration, float acceleration, float maxSpeed) {
        this.deceleration = deceleration;
        this.acceleration = acceleration;
        this.maxSpeed = maxSpeed;
    }

    // Constructor for player
    public MovingPart(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }


    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }

    public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setSpeed(float speed) {
        this.acceleration = speed;
        this.maxSpeed = speed;
    }

    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }


    @Override
    public void process(GameData gameData, Entity entity) {
        float dt = gameData.getDelta();
        CanonPart canonPart = entity.getPart(CanonPart.class);
        if (canonPart != null) {
            float radians = canonPart.getRadian();

            if (right && radians > 0) {  // Rotate clockwise, decrease radians but not below 0
                radians -= rotationSpeed * dt;
                if (radians < 0) {
                    radians = 0;  // Prevent going below 0 radians
                }
            }

            if (left && radians < Math.PI / 2) {  // Rotate counterclockwise, increase radians but not above π/2
                radians += rotationSpeed * dt;
                if (radians > Math.PI / 2) {
                    radians = (float) (Math.PI / 2);  // Prevent going above π/2 radians
                }
            }
            canonPart.setRadian(radians);
        }

        /*

        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        // accelerating
        if (up) {
            dx += cos(radians) * acceleration * dt;
            dy += sin(radians) * acceleration * dt;
        }

        // deccelerating
        float vec = (float) sqrt(dx * dx + dy * dy);
        if (vec > 0) {
            dx -= (dx / vec) * deceleration * dt;
            dy -= (dy / vec) * deceleration * dt;
        }
        if (vec > maxSpeed) {
            dx = (dx / vec) * maxSpeed;
            dy = (dy / vec) * maxSpeed;
        }

        // set position
        x += dx * dt;
        y += dy * dt;

        positionPart.setX(x);
        positionPart.setY(y);

        positionPart.setRadians(radians);

         */

    }
}

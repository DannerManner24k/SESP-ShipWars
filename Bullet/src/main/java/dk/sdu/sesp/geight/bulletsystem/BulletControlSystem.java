package dk.sdu.sesp.geight.bulletsystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.CanonPart;
import dk.sdu.sesp.geight.common.data.entityparts.MovingPart;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;
import du.sdu.sesp.geight.common.bullet.Bullet;
import du.sdu.sesp.geight.common.bullet.BulletSPI;
import du.sdu.sesp.geight.common.bullet.Vector2D;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    private static final float GRAVITY = 9.81f;
    private static final float MAX_VELOCITY = 600 / 4.0f;
    private static final float TIME_SCALE = 1000000f;
    private static final float dt = 1/60f;
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities(Bullet.class)) {
            Bullet bullet = (Bullet) entity;
            updateBullet(bullet);

            PositionPart positionPart = entity.getPart(PositionPart.class);
            //System.out.println(gameData.getDelta());
            //System.out.println("Bullet position: " + positionPart.getX() + ", " + positionPart.getY());
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        CanonPart canonPart = shooter.getPart(CanonPart.class);

        float x = canonPart.getX();
        float y = canonPart.getY();
        float radians = canonPart.getRadian();

        Bullet bullet = new Bullet();
        bullet.add(new PositionPart(x, y, radians));

        // Initialize the bullet with a strength and angle
        int strength = 50; // Example strength value, this can be set dynamically
        float angle = (float) Math.toDegrees(radians);

        initializeBullet(bullet, strength, angle);

        float[] shapeX = new float[2];
        float[] shapeY = new float[2];

        bullet.setShapeX(shapeX);
        bullet.setShapeY(shapeY);

        return bullet;
    }

    private void setShape(Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        CanonPart canonPart = entity.getPart(CanonPart.class);
        float radians = canonPart.getRadian();

        /*
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();

        shapex[0] = x;
        shapey[0] = y;

        shapex[1] = (float) (x + radians);
        shapey[1] = (float) (y + radians);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);

         */
    }

    // Update each bullet's position and remove inactive ones
    public void updateBullet(Bullet bullet) {
        /*if (bullet == null || !bullet.isActive()) {
            return;
        }

         */

        PositionPart positionPart = bullet.getPart(PositionPart.class);
        Vector2D velocity = bullet.getVelocity();

        if (positionPart == null || velocity == null) {
            return;
        }

        // Update position based on velocity and deltaTime
        positionPart.setX(positionPart.getX() + velocity.getX() * dt);
        positionPart.setY(positionPart.getY() + velocity.getY() * dt);

        // Update velocity based on gravity
        velocity.setY(velocity.getY() - GRAVITY * dt);

        // Check if the bullet has hit the ground or gone out of bounds
        /*if (positionPart.getY() <= 0 || positionPart.getX() < 0 || positionPart.getX() > 800) {
            bullet.setActive(false); // Mark the bullet as inactive
        }

         */
    }

    // Initialize the bullet with initial velocity based on strength and angle
    public void initializeBullet(Bullet bullet, int strength, float angle) {
        float initialVelocity = (strength / 100.0f) * MAX_VELOCITY;
        float radianAngle = (float) Math.toRadians(angle);

        float velocityX = (float) (initialVelocity * Math.cos(radianAngle));
        float velocityY = (float) (initialVelocity * Math.sin(radianAngle));

        Vector2D velocity = bullet.getVelocity();
        if (velocity == null) {
            velocity = new Vector2D(velocityX, velocityY);
            bullet.setVelocity(velocity);
        } else {
            velocity.set(velocityX, velocityY);
        }
    }

}

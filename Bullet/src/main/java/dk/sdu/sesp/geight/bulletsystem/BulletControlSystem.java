package dk.sdu.sesp.geight.bulletsystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.*;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;
import du.sdu.sesp.geight.common.bullet.Bullet;
import du.sdu.sesp.geight.common.bullet.BulletSPI;
import du.sdu.sesp.geight.common.bullet.Vector2D;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {
    private static List<Bullet> activeBullets = new ArrayList<>();

    private static final float GRAVITY = 9.81f;
    private static final float MAX_VELOCITY = 600 / 4.0f;
    private static final float dt = 1/60f;
    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            PositionPart positionPart = bullet.getPart(PositionPart.class);
            MovingPart movingPart = bullet.getPart(MovingPart.class);
            TimerPart timerPart = bullet.getPart(TimerPart.class);
            movingPart.setUp(true);
            if (timerPart.getExpiration() < 0) {
                world.removeEntity(bullet);
            }
            timerPart.process(gameData, bullet);
            movingPart.process(gameData, bullet);
            positionPart.process(gameData, bullet);

            setShape(bullet);

            updateBullets((Bullet) bullet, gameData.getDelta());
            //System.out.println("Bullet position: " + canonPart.getX() + ", " + canonPart.getY());
        }
    }

    @Override
    public Entity createBullet(Entity entity, GameData gameData) {
        CanonPart canonPart = entity.getPart(CanonPart.class);
        float x = canonPart.getX();
        float y = canonPart.getY();
        float radians = canonPart.getRadian();
        float speed = 350;

        Entity bullet = new Bullet();

        bullet.setRadius(2);

        float bx = (float) cos(radians) * bullet.getRadius() * 8;
        float by = (float) sin(radians) * bullet.getRadius() * 8;

        bullet.add(new PositionPart(bx + x, by + y, radians));
        bullet.add(new LifePart(1));
        bullet.add(new MovingPart(0, 5000000, speed));
        bullet.add(new TimerPart(1));


        bullet.setShapeX(new float[2]);
        bullet.setShapeY(new float[2]);

        return bullet;
    }

    private void setShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = x;
        shapey[0] = y;

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5));
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5));

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
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

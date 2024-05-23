package dk.sdu.sesp.geight.weaponsystem.bulletsystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.*;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;
import dk.sdu.sesp.geight.common.weapon.bullet.Bullet;
import dk.sdu.sesp.geight.common.weapon.bullet.BulletSPI;
import dk.sdu.sesp.geight.common.weapon.bullet.Vector2D;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class BulletControlSystem implements IEntityProcessingService, BulletSPI {

    private static final float GRAVITY = 9.81f;
    private static final float MAX_VELOCITY = 600 / 4.0f;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities(Bullet.class)) {
            Bullet bullet = (Bullet) entity; // Casting Bullet entity to a Bullet
            PositionPart positionPart = bullet.getPart(PositionPart.class);

            TimerPart timerPart = bullet.getPart(TimerPart.class);
            if (timerPart.getExpiration() < 0) {
                world.removeEntity(bullet);
            }
            timerPart.process(gameData, bullet);



            positionPart.process(gameData, bullet);

            updateBullet(bullet, gameData);
        }
    }

    @Override
    public Entity createBullet(Entity entity, GameData gameData, float strength) {
        CanonPart canonPart = entity.getPart(CanonPart.class);
        float x = canonPart.getX();
        float y = canonPart.getY();
        float radians = canonPart.getRadian();

        Bullet bullet = new Bullet();
        bullet.setRadius(2);

        float bx = (float) cos(radians) * bullet.getRadius() * 8;
        float by = (float) sin(radians) * bullet.getRadius() * 8;

        bullet.add(new PositionPart(bx + x, by + y, radians));
        bullet.add(new LifePart(1));
        bullet.add(new TimerPart(3));


        bullet.setStrength(strength);

        float angle = (float) Math.toDegrees(radians);

        initializeBullet(bullet, strength, angle);

        float[] shapeX = new float[2];
        float[] shapeY = new float[2];


        bullet.setShapeX(new float[2]);
        bullet.setShapeY(new float[2]);

        return bullet;
    }

    private void setShape(Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        CanonPart canonPart = entity.getPart(CanonPart.class);
        float radians = canonPart.getRadian();

    }

    // Update each bullet's position and remove inactive ones
    public void updateBullet(Bullet bullet, GameData gameData) {
        float dt = gameData.getDelta()*2;
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

    }

    // Initialize the bullet with initial velocity based on strength and angle
    public void initializeBullet(Bullet bullet, float strength, float angle) {
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

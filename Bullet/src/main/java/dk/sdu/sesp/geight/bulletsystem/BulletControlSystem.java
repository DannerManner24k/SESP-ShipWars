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
    public static void updateBullets(Bullet bullet, float deltaTime) {
        PositionPart positionPart = bullet.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        float initialVelocity = 10 * bullet.getStrength();
        CanonPart canonPart = bullet.getPart(Bullet.class);
        float radians = canonPart.getRadian();

        bullet.setVelocityX(initialVelocity * (float)Math.cos(radians));
        bullet.setVelocityY(initialVelocity * (float)Math.sin(radians));

        float GRAVITY = 9.8f;

        positionPart.setX(x + bullet.getVelocityX() * deltaTime);
        positionPart.setY(y + bullet.getVelocityY() * deltaTime - 0.5f * GRAVITY  * deltaTime * deltaTime);
        bullet.setVelocityY(bullet.getVelocityY() - GRAVITY * deltaTime);
    }

}

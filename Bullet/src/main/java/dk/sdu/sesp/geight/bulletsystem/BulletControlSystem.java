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
    private static List<Bullet> activeBullets = new ArrayList<>();

    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            CanonPart canonPart = bullet.getPart(CanonPart.class);

            //canonPart.process(gameData, bullet);

            setShape(bullet);

            updateBullets((Bullet) bullet, gameData.getDelta());
            //System.out.println("Bullet position: " + canonPart.getX() + ", " + canonPart.getY());
        }
    }

    @Override
    public Entity createBullet(Entity entity, GameData gameData) {
        System.out.println("Creating bullet");
        CanonPart canonPart = entity.getPart(CanonPart.class);

        float x = canonPart.getX();
        float y = canonPart.getY();
        float radians = canonPart.getRadian();
        System.out.println("Canon radians: " + radians);

        Entity bullet = new Bullet();

        bullet.add(new PositionPart(x, y, radians));
        bullet.add(new CanonPart(x, y, radians));
        ((Bullet) bullet).setStrength(50);
        //bullet.add(new MovingPart(0, 5000000, speed, 5));

        float [] shapeX = new float[2];
        float [] shapeY = new float[2];

        bullet.setShapeX(shapeX);
        bullet.setShapeY(shapeY);

        return bullet;
    }

    private void setShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        CanonPart canonPart = entity.getPart(CanonPart.class);
        float radians = canonPart.getRadian();

        shapex[0] = x;
        shapey[0] = y;

        shapex[1] = (float) (x + radians);
        shapey[1] = (float) (y + radians);

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

    // Add a new bullet to the list
    public static void addBullet(Bullet bullet) {
        activeBullets.add(bullet);
        System.out.println("Bullet added: " + bullet);
    }

    // Update each bullet's position and remove inactive ones
    public static void updateBullets(Bullet bullet, float deltaTime) {
        PositionPart positionPart = bullet.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        float initialVelocity = 10 * bullet.getStrength();

        CanonPart canonPart = bullet.getPart(CanonPart.class);
        float radians = canonPart.getRadian();

        bullet.setVelocityX(initialVelocity * (float)Math.cos(radians));
        bullet.setVelocityY(initialVelocity * (float)Math.sin(radians));

        float GRAVITY = 9.8f;

        positionPart.setX(x + bullet.getVelocityX() * deltaTime);
        positionPart.setY(y + bullet.getVelocityY() * deltaTime - 0.5f * GRAVITY  * deltaTime * deltaTime);
        bullet.setVelocityY(bullet.getVelocityY() - GRAVITY * deltaTime);

        System.out.println("Bullet position: " + positionPart.getX() + ", " + positionPart.getY());
    }

    // Clear all active bullets
    public static void clearBullets() {
        activeBullets.clear();
    }

}

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

            canonPart.process(gameData, bullet);

            setShape(bullet);

            System.out.println("Bullet position: " + canonPart.getX() + ", " + canonPart.getY());
        }
    }

    @Override
    public Entity createBullet(Entity entity, GameData gameData) {
        System.out.println("Creating bullet");
        CanonPart canonPart = entity.getPart(CanonPart.class);

        float x = canonPart.getX();
        float y = canonPart.getY();
        float radians = canonPart.getRadian();

        Entity bullet = new Bullet();

        //float bx = (float) cos(radians) * entity.getRadius() * bullet.getRadius();
        //float by = (float) sin(radians) * entity.getRadius() * bullet.getRadius();

        bullet.add(new PositionPart(x, y, radians));
        bullet.add(new CanonPart(x, y, radians));
        //bullet.add(new MovingPart(0, 5000000, speed, 5));

        float [] shapeX = new float[2];
        float [] shapeY = new float[2];
        /*
        shapeX[0] = x;
        shapeY[0] = y;

        shapeX[1] = x+10;
        shapeY[1] = y+10;

        shapeX[2] = x;
        shapeY[2] = y;

         */

        bullet.setShapeX(shapeX);
        bullet.setShapeY(shapeY);

        return bullet;
    }

    private void setShape(Entity entity) {
        float[] shapex = entity.getShapeX();
        float[] shapey = entity.getShapeY();
        CanonPart canonPart = entity.getPart(CanonPart.class);
        float x = canonPart.getX();
        float y = canonPart.getY();
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
    public static void updateBullets(float deltaTime) {
        List<Bullet> toRemove = new ArrayList<>();
        for (Bullet bullet : activeBullets) {
            bullet.update(deltaTime);
            if (!bullet.isActive()) {
                toRemove.add(bullet);
            }
        }
        activeBullets.removeAll(toRemove);
    }

    // Clear all active bullets
    public static void clearBullets() {
        activeBullets.clear();
    }

}

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
            PositionPart positionPart = bullet.getPart(PositionPart.class);

            float x = positionPart.getX();
            float y = positionPart.getY();

            float speed = ((Bullet) bullet).getSpeed();
            float radians = positionPart.getRadians();

            double changeX = Math.cos(radians);
            double changeY = Math.sin(Math.toRadians(radians));

            positionPart.setX(x + (float) changeX * speed);
            positionPart.setY(y + (float) changeY * speed);

            /*
            Vector2D velocity = new Vector2D((float) (speed * Math.cos(radians)), (float) (speed * Math.sin(radians)));

            ((Bullet) bullet).setVelocity(velocity);
            positionPart.setX(x + velocity.getX());
            positionPart.setY(y + velocity.getY());

             */
        }
    }

    @Override
    public Entity createBullet(Entity entity, GameData gameData) {
        System.out.println("Creating bullet");
        CanonPart shooterPos = entity.getPart(CanonPart.class);

        float x = shooterPos.getX();
        float y = shooterPos.getY();
        float radians = shooterPos.getRadian();
        float dt = gameData.getDelta();
        float speed = 350;

        Entity bullet = new Bullet();
        bullet.setRadius(2);

        float bx = (float) cos(radians) * entity.getRadius() * bullet.getRadius();
        float by = (float) sin(radians) * entity.getRadius() * bullet.getRadius();

        bullet.add(new PositionPart(bx + x, by + y, radians));
        //bullet.add(new MovingPart(0, 5000000, speed, 5));

        float [] shapeX = new float[3];
        float [] shapeY = new float[3];

        shapeX[0] = x;
        shapeY[0] = y;

        shapeX[1] = x+10;
        shapeY[1] = y+10;

        shapeX[2] = x;
        shapeY[2] = y;

        bullet.setShapeX(shapeX);
        bullet.setShapeY(shapeY);

        return bullet;
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

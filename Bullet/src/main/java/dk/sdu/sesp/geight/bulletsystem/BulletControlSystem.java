package dk.sdu.sesp.geight.bulletsystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;
import du.sdu.sesp.geight.common.bullet.Bullet;

import java.util.ArrayList;
import java.util.List;

public class BulletControlSystem implements IEntityProcessingService {
    private static List<Bullet> activeBullets = new ArrayList<>();

    @Override
    public void process(GameData gameData, World world) {
        List<Entity> toRemove = new ArrayList<>();
        for (Entity entity : world.getEntities(Bullet.class)) {
            Bullet bullet = (Bullet) entity;
            bullet.update(gameData.getDelta());

            if (!bullet.isActive()) {
                toRemove.add(bullet);
            } else {
                updateBullets(bullet);
            }
        }
        for (Entity entity : toRemove) {
            world.removeEntity(entity);
        }
    }

    // Add a new bullet to the list
    public static void addBullet(Bullet bullet) {
        activeBullets.add(bullet);
        System.out.println("Bullet added: " + bullet);
    }

    // Update each bullet's position and remove inactive ones
    public static void updateBullets(Entity entity) {
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = x + (float) Math.cos(radians) * 4;
        shapey[0] = y + (float) Math.sin(radians) * 4;

        shapex[1] = x + (float) Math.cos(radians + Math.PI / 2) * 4;
        shapey[1] = y + (float) Math.sin(radians + Math.PI / 2) * 4;

        shapex[2] = x + (float) Math.cos(radians + Math.PI) * 4;
        shapey[2] = y + (float) Math.sin(radians + Math.PI) * 4;

        shapex[3] = x + (float) Math.cos(radians - Math.PI / 2) * 4;
        shapey[3] = y + (float) Math.sin(radians - Math.PI / 2) * 4;

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }


    // Clear all active bullets
    public static void clearBullets() {
        activeBullets.clear();
    }

    // Check collisions between bullets and entities (e.g., players or enemies)
    public static void checkCollisions(List<Entity> entities) {
        for (Bullet bullet : activeBullets) {
            for (Entity entity : entities) {
                /*
                // Example collision detection logic
                if (bullet.getPosition().dst(entity.getPosition()) < bullet.getWidth()) {
                    System.out.println("Bullet hit an entity!");
                }

                 */
            }
        }
    }

}


package dk.sdu.sesp.geight.collisionsystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;
import dk.sdu.sesp.geight.common.services.ICollidable;
import dk.sdu.sesp.geight.common.services.IPostEntityProcessingService;
import dk.sdu.sesp.geight.common.services.collision.IBullet;
import dk.sdu.sesp.geight.common.services.collision.ITerrain;

import java.util.ArrayList;
import java.util.List;

public class CollisionProcessor implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        List<ICollidable> collidableEntities = new ArrayList<>();

        // Collect all collidable entities
        for (Entity entity : world.getEntities()) {
            if (entity instanceof ICollidable) {
                collidableEntities.add((ICollidable) entity);
            }
        }

        // Check for collisions between collidable entities
        for (int i = 0; i < collidableEntities.size(); i++) {
            ICollidable collidable1 = collidableEntities.get(i);
            for (int j = i + 1; j < collidableEntities.size(); j++) {
                ICollidable collidable2 = collidableEntities.get(j);
                if (collides((Entity) collidable1, (Entity) collidable2)) {
                    if (collidable1 instanceof IBullet && collidable2 instanceof IBullet) {
                        continue;
                    }
                    collidable1.onCollision(gameData, world, collidable1, collidable2);
                    collidable2.onCollision(gameData, world, collidable2, collidable1);


                    // Handle specific collision case for bullet and terrain
                    if (collidable1 instanceof IBullet && collidable2 instanceof ITerrain) {
                        handleBulletTerrainCollision((IBullet) collidable1, (ITerrain) collidable2, world);
                    } else if (collidable2 instanceof IBullet && collidable1 instanceof ITerrain) {
                        handleBulletTerrainCollision((IBullet) collidable2, (ITerrain) collidable1, world);
                    }
                }
            }
        }
    }

    private boolean collides(Entity entity1, Entity entity2) {
        PositionPart pos1 = entity1.getPart(PositionPart.class);
        PositionPart pos2 = entity2.getPart(PositionPart.class);

        if (pos1 == null && !(entity1 instanceof ITerrain)) {
            return false;
        }
        if (pos2 == null && !(entity2 instanceof ITerrain)) {
            return false;
        }

        if (entity1 instanceof ITerrain || entity2 instanceof ITerrain) {
            return handleTerrainCollision(entity1, entity2);
        }

        float dx = pos1.getX() - pos2.getX();
        float dy = pos1.getY() - pos2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

    private boolean handleTerrainCollision(Entity entity1, Entity entity2) {
        Entity bullet = entity1 instanceof IBullet ? entity1 : entity2;
        ITerrain terrain = entity1 instanceof ITerrain ? (ITerrain) entity1 : (ITerrain) entity2;

        PositionPart pos = bullet.getPart(PositionPart.class);
        if (pos == null) {
            return false;
        }

        float bulletX = pos.getX();
        float bulletY = pos.getY();
        double[] heights = terrain.getHeights();

        // Adjust mapping if necessary
        int terrainIndex = Math.round(bulletX);
        if (terrainIndex >= 0 && terrainIndex < heights.length) {
            if (bulletY <= heights[terrainIndex]) {
                return true;
            }
        }
        return false;
    }

    private void handleBulletTerrainCollision(IBullet bullet, ITerrain terrain, World world) {
        PositionPart pos = ((Entity) bullet).getPart(PositionPart.class);
        float bulletX = pos.getX();
        float bulletY = pos.getY();
        double[] heights = terrain.getHeights();

        // Assuming the terrain heights array covers the range of bullet positions
        int terrainIndex = Math.round(bulletX); // Adjust mapping if necessary
        if (terrainIndex >= 0 && terrainIndex < heights.length) {
            if (bulletY <= heights[terrainIndex]) {
                terrain.createCrater(terrainIndex, 10);
            }
        }
    }
}

package dk.sdu.sesp.geight.collisionsystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;
import dk.sdu.sesp.geight.common.services.ICollidable;
import dk.sdu.sesp.geight.common.services.IPostEntityProcessingService;
import dk.sdu.sesp.geight.common.services.collision.IBullet;
import dk.sdu.sesp.geight.common.services.collision.ITerrain;


public class CollisionProcessor implements IPostEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof ICollidable) {
                checkCollisions((ICollidable) entity, world, gameData);
            }
        }
    }

    private void checkCollisions(ICollidable collidable, World world, GameData gameData) {
        for (Entity entity : world.getEntities()) {
            if (entity.getID().equals(((Entity) collidable).getID())) {
                continue;
            }

            if (entity instanceof ICollidable && collides((Entity) collidable, (Entity) entity)) {
                collidable.onCollision(gameData, world, (ICollidable) entity);
                ((ICollidable) entity).onCollision(gameData, world, collidable);

                if (collidable instanceof IBullet && entity instanceof ITerrain) {
                    handleBulletTerrainCollision((IBullet) collidable, (ITerrain) entity, world);
                }
            }
        }
    }

    private boolean collides(Entity entity1, Entity entity2) {
        PositionPart pos1 = entity1.getPart(PositionPart.class);
        PositionPart pos2 = entity2.getPart(PositionPart.class);

        // If either entity does not have a PositionPart, they cannot collide in the traditional sense
        if (pos1 == null || pos2 == null) {
            return false;
        }

        float dx = pos1.getX() - pos2.getX();
        float dy = pos1.getY() - pos2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        return distance < (entity1.getRadius() + entity2.getRadius());
    }

    private void handleBulletTerrainCollision(IBullet bullet, ITerrain terrain, World world) {
        PositionPart pos = ((Entity) bullet).getPart(PositionPart.class);
        float bulletX = pos.getX();
        float bulletY = pos.getY();
        double[] heights = terrain.getHeights();

        for (int i = 0; i < heights.length; i++) {
            if (bulletX == i && bulletY <= heights[i]) {
                terrain.createCrater(i, 10);
                world.removeEntity((Entity) bullet);
                break;
            }
        }
    }
}

    /*
    @Override
    public void process(GameData gameData, World world) {
        // two for loops for all entities in the world
        for (Entity entity : world.getEntities()) {
            for (Entity collisionDetection : world.getEntities()) {
                // get life parts on all entities
                LifePart entityLife = entity.getPart(LifePart.class);

                // if the two entities are identical, skip the iteration
                if (entity.getID().equals(collisionDetection.getID())) {
                    continue;
                    // remove entities with zero in expiration
                }

                // CollisionDetection
                if (this.collides(entity, collisionDetection)) {
                    // if entity has been hit, and should have its life reduced
                    if (entityLife.getLife() > 0) {
                        entityLife.setLife(entityLife.getLife() - 1);
                        entityLife.setIsHit(true);
                        // if entity is out of life - remove
                        if (entityLife.getLife() <= 0) {
                            world.removeEntity(entity);
                        }
                    }
                }
            }
        }
    }

    public void update(World world) {
        for (Entity entity1 : world.getEntities()) {
            for (Entity entity2 : world.getEntities()) {
                if (!entity1.getID().equals(entity2.getID()) && this.checkCollision(entity1, entity2)) {
                    handleCollision(world, entity1, entity2);
                }
            }
        }
    }

    // Check if two entities are colliding
    private boolean checkCollision(Entity entity1, Entity entity2) {
        PositionPart positionPart1 = entity1.getPart(PositionPart.class);
        PositionPart positionPart2 = entity2.getPart(PositionPart.class);
        double dx = positionPart1.getX() - positionPart2.getX();
        double dy = positionPart1.getY() - positionPart2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < (entity1.getRadius() + entity2.getRadius());
    }

    public Boolean collides(Entity entity, Entity entity2) {
        PositionPart entMov = entity.getPart(PositionPart.class);
        PositionPart entMov2 = entity2.getPart(PositionPart.class);
        float dx = (float) entMov.getX() - (float) entMov2.getX();
        float dy = (float) entMov.getY() - (float) entMov2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        if (distance < (entity.getRadius() + entity2.getRadius())) {
            return true;
        }
        return false;
    }

    // Handle the logic when two entities collide
    private void handleCollision(World world, Entity firstEntity, Entity secondEntity) {
        System.out.println("Collision detected between " + firstEntity + " and " + secondEntity);
    }

     */


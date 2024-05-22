package dk.sdu.sesp.geight.collisionsystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.LifePart;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;
import dk.sdu.sesp.geight.common.services.IPostEntityProcessingService;

import java.util.List;
import java.util.Map;

public class CollisionProcessor implements IPostEntityProcessingService {

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
}

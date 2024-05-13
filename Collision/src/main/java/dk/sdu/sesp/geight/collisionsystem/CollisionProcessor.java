package dk.sdu.sesp.geight.collisionsystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;

import java.util.List;
public class CollisionProcessor {

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

    // Handle the logic when two entities collide
    private void handleCollision(World world, Entity firstEntity, Entity secondEntity) {
        System.out.println("Collision detected between " + firstEntity + " and " + secondEntity);
    }
}

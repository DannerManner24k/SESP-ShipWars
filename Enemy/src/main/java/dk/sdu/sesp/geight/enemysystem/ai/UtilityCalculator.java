package dk.sdu.sesp.geight.enemysystem.ai;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.CanonPart;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;
import dk.sdu.sesp.geight.playersystem.Player;

public class UtilityCalculator {

    public double calculateUtility(Action action, Entity enemy, GameData gameData, World world) {
        if (action instanceof RotateCanonAction) {
            return calculateRotateUtility(enemy, gameData, world);
        } else if (action instanceof ShootAtPlayerAction) {
            return calculateShootUtility(enemy, gameData, world);
        }
        return 0.0;
    }

    private double calculateRotateUtility(Entity enemy, GameData gameData, World world) {
        // Logic to calculate utility of rotating the cannon towards the player
        return 1.0; // Placeholder value
    }

    private double calculateShootUtility(Entity enemy, GameData gameData, World world) {
        PositionPart enemyPos = enemy.getPart(PositionPart.class);
        CanonPart canonPart = enemy.getPart(CanonPart.class);
        PositionPart playerPos = getPlayerPosition(world);

        float dx = playerPos.getX() - enemyPos.getX();
        float dy = playerPos.getY() - enemyPos.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);
        float angleToPlayer = (float) Math.atan2(dy, dx);
        float angleDifference = Math.abs(angleToPlayer - canonPart.getRadian());

        // Higher utility if the cannon is already facing the player and the player is within range
        return (1.0 / (distance + 1)) * (1.0 / (angleDifference + 1));
    }

    private PositionPart getPlayerPosition(World world) {
        // Assuming there's only one player entity
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Player) { // Assuming Player is a class in your game
                return entity.getPart(PositionPart.class);
            }
        }
        return null;
    }
}

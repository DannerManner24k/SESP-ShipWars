package dk.sdu.sesp.geight.enemysystem.ai;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;
import dk.sdu.sesp.geight.playersystem.Player;

import java.util.Random;

public class EnemyAI {

    private static final float GRAVITY = 9.81f;
    private static final float MAX_VELOCITY = 600 / 4.0f;
    private final Random random = new Random();

    public float[] calculateBestShot(Entity enemy, GameData gameData, World world, int accuracyLevel) {
        PositionPart enemyPos = enemy.getPart(PositionPart.class);

        Entity player = getPlayerEntity(world);
        if (player != null) {
            PositionPart playerPos = player.getPart(PositionPart.class);
            if (playerPos != null) {
                float deltaX = playerPos.getX() - enemyPos.getX();
                float deltaY = playerPos.getY() - enemyPos.getY();
                float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

                // Calculate the optimal launch angle
                float optimalAngle = calculateOptimalAngle(deltaX, deltaY, distance);

                // Calculate the required initial velocity
                float initialVelocity = calculateInitialVelocity(distance, optimalAngle);

                // Introduce randomness based on accuracy level
                float adjustedAngle = applyAccuracy(optimalAngle, accuracyLevel);
                float adjustedVelocity = applyAccuracy(initialVelocity, accuracyLevel);

                // Calculate power based on the initial velocity
                float power = (adjustedVelocity / MAX_VELOCITY) * 100;

                return new float[]{adjustedAngle, power};
            }
        }
        return null;
    }

    private float calculateInitialVelocity(float distance, float angle) {
        // Calculate the initial velocity required to reach the target distance at the given angle
        float velocity = (float) Math.sqrt((GRAVITY * distance) / (Math.sin(2 * angle)));
        return Math.min(velocity, MAX_VELOCITY);
    }

    private float calculateOptimalAngle(float deltaX, float deltaY, float distance) {
        // Calculate the angle required to hit the target considering gravity
        float timeOfFlight = distance / MAX_VELOCITY;
        float drop = 0.5f * GRAVITY * timeOfFlight * timeOfFlight;
        return (float) Math.atan2(deltaY + drop, deltaX);
    }

    private float applyAccuracy(float value, int accuracyLevel) {
        float variance = 0.1f; // Default variance for accuracy level 0 (least accurate)
        if (accuracyLevel == 1) {
            variance = 0.05f; // Moderate variance for accuracy level 1
        } else if (accuracyLevel == 2) {
            variance = 0.01f; // Minimal variance for accuracy level 2 (most accurate)
        }
        return value + (random.nextFloat() - 0.5f) * variance;
    }

    private Entity getPlayerEntity(World world) {
        for (Entity entity : world.getEntities()) {
            if (entity instanceof Player) {
                return entity;
            }
        }
        return null;
    }
}

package dk.sdu.sesp.geight.enemysystem.ai;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;

import java.util.Random;

public class EnemyAI {

    private static final float GRAVITY = 9.81f;
    private static final float MAX_VELOCITY = 600 / 4.0f;
    private final Random random = new Random();

    // Define utility function weights
    private final UtilityFunction utilityFunction = new UtilityFunction(0.4, 0.3, 0.3);

    // Static coordinates for the player
    private static final float PLAYER_X = 20.0f;
    private static final float PLAYER_Y = 150.0f;

    public float[] calculateBestShot(Entity enemy, GameData gameData, World world, int accuracyLevel) {
        PositionPart enemyPos = enemy.getPart(PositionPart.class);

        float deltaX = PLAYER_X - enemyPos.getX();
        float deltaY = PLAYER_Y - enemyPos.getY();
        System.out.println("Position of the player is: " + PLAYER_X + " and " + PLAYER_Y);
        float distance = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);

        // Calculate the optimal launch angle
        float optimalAngle = calculateOptimalAngle(deltaX, deltaY, distance);

        // Calculate the required initial velocity
        float initialVelocity = calculateInitialVelocity(distance, optimalAngle);

        // Check for valid velocity
        if (Float.isNaN(initialVelocity) || initialVelocity <= 0) {
            System.err.println("Invalid initial velocity calculated: " + initialVelocity);
            initialVelocity = 100f;
        }

        // Introduce randomness based on accuracy level
        float adjustedAngle = applyAccuracy(optimalAngle, accuracyLevel);
        float adjustedVelocity = applyAccuracy(initialVelocity, accuracyLevel);

        // Calculate power based on the initial velocity
        float power = (adjustedVelocity / MAX_VELOCITY) * 100;

        // Calculate utility for the shot
        double utility = utilityFunction.calculateUtility(distance, adjustedAngle, power);

        // Print statement for recalculating shot and utility
        System.out.println("EnemyAI: recalculating shot with utility: " + utility);
        System.out.println("EnemyAI: distance=" + distance + ", angle=" + adjustedAngle + ", power=" + power);

        return new float[]{adjustedAngle, power};
    }

    private float calculateInitialVelocity(float distance, float angle) {
        // Calculate the initial velocity required to reach the target distance at the given angle
        // Ensure the angle is in radians
        float sin2Angle = (float) Math.sin(2 * angle);

        if (sin2Angle <= 0) {
            return Float.NaN; // Avoid division by zero and ensure positive sin2Angle
        }

        float velocity = (float) Math.sqrt((GRAVITY * distance) / sin2Angle);
        return Math.min(velocity, MAX_VELOCITY);
    }

    private float calculateOptimalAngle(float deltaX, float deltaY, float distance) {
        // Calculate the angle required to hit the target considering gravity
        float timeOfFlight = distance / MAX_VELOCITY;
        float drop = 0.5f * GRAVITY * timeOfFlight * timeOfFlight;
        return (float) Math.atan2(deltaY + drop, deltaX);
    }

    private float applyAccuracy(float value, int accuracyLevel) {
        float variance;
        switch (accuracyLevel) {
            case 0:
                variance = 1.0f; // Least accurate
                break;
            case 1:
                variance = 0.5f; // More accurate
                break;
            case 2:
                variance = 0.2f; // Even more accurate
                break;
            case 3:
                variance = 0.05f; // Highly accurate
                break;
            case 4:
                variance = 0.01f; // Most accurate, 99% accuracy
                break;
            default:
                variance = 1.0f; // Default to least accurate
                break;
        }
        return value + (random.nextFloat() - 0.5f) * variance;
    }
}

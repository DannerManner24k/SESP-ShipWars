package dk.sdu.sesp.geight.enemysystem.ai;

public class UtilityFunction {

    // Weights for each parameter in the utility function
    private final double distanceWeight;
    private final double angleWeight;
    private final double powerWeight;

    public UtilityFunction(double distanceWeight, double angleWeight, double powerWeight) {
        this.distanceWeight = distanceWeight;
        this.angleWeight = angleWeight;
        this.powerWeight = powerWeight;
    }

    // Calculates the utility value based on the provided parameters
    public double calculateUtility(float distance, float angle, float power) {
        double utility = (distanceWeight * normalizeDistance(distance)) +
                (angleWeight * normalizeAngle(angle)) +
                (powerWeight * normalizePower(power));
        return utility;
    }

    // Normalization functions to bring values into a common scale (0 to 1)
    private double normalizeDistance(float distance) {
        // Assuming a max distance for normalization purposes
        float maxDistance = 1000.0f;
        return Math.min(distance / maxDistance, 1.0);
    }

    private double normalizeAngle(float angle) {
        // Angle normalization assuming max angle is π (180 degrees)
        float maxAngle = (float) Math.PI;
        return Math.abs(angle) / maxAngle;
    }

    private double normalizePower(float power) {
        // Assuming max power is 100 for normalization purposes
        float maxPower = 100.0f;
        return power / maxPower;
    }
}

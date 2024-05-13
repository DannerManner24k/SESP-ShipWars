package du.sdu.sesp.geight.common.bullet;

public class Vector2D {
    public float x;
    public float y;

    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Sets the vector's components
    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Adds another vector to this vector
    public void add(Vector2D other) {
        this.x += other.x;
        this.y += other.y;
    }

    // Scales the vector by a scalar
    public void scale(float scaleFactor) {
        this.x *= scaleFactor;
        this.y *= scaleFactor;
    }

    // Returns the length (magnitude) of the vector
    public float length() {
        return (float)Math.sqrt(x * x + y * y);
    }

    // Rotates the vector by a given angle in degrees
    public void rotate(float angleDegrees) {
        double radians = Math.toRadians(angleDegrees);
        float cos = (float)Math.cos(radians);
        float sin = (float)Math.sin(radians);

        float newX = this.x * cos - this.y * sin;
        float newY = this.x * sin + this.y * cos;

        this.x = newX;
        this.y = newY;
    }

    public void add(float dx, float dy) {
        x += dx;
        y += dy;
    }
}


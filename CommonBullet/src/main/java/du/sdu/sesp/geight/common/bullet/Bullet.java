package du.sdu.sesp.geight.common.bullet;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.common.data.Entity;

public class Bullet extends Entity {
    private int damage;
    private float speed;
    private boolean isActive;

    public Bullet(float x, float y, float width, float height, int damage, float speed) {
        super(x, y, width, height);
        this.damage = damage;
        this.speed = speed;
    }

    @Override
    public void render(SpriteBatch batch) {

    }

    public void update() {
        // Example movement logic, assuming simple horizontal movement
        super.position.x += speed;
    }

    public boolean isActive() {
        return isActive;
    }

    public void deactivate() {
        isActive = false;
    }
}

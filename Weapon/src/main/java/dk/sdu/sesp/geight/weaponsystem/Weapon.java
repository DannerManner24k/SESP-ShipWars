package dk.sdu.sesp.geight.weaponsystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.common.data.Entity;
import du.sdu.sesp.geight.common.bullet.Bullet;

public abstract class Weapon extends Entity {
    private String name;
    private Bullet bullet;
    private boolean canFire;

    private Weapon(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    @Override
    public void render(SpriteBatch batch) {

    }
    public void fire() {
        if (canFire == true) {

            System.out.println(name + " fired!");
            onFire();
        }
    }

    public String getName() {
        return name;
    }

    public boolean getCanFire() {
        return canFire;
    }
    protected abstract void onFire();
}

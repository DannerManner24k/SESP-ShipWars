package dk.sdu.sesp.geight.common.weapon;

import dk.sdu.sesp.geight.common.data.Entity;
import du.sdu.sesp.geight.common.bullet.Bullet;

public abstract class Weapon extends Entity {
    private String name;
    private Bullet bullet;
    private boolean canFire;

    public void fire() {
        if (canFire == true) {

            System.out.println(name + " fired!");
            onFire();
        }
    }

    public String getName() {
        return name;
    }

    public void setCanFire(boolean canFire) {
        this.canFire = canFire;
    }

    public boolean getCanFire() {
        return canFire;
    }
    protected abstract void onFire();
}

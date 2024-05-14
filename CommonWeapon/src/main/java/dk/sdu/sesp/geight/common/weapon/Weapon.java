package dk.sdu.sesp.geight.common.weapon;

import dk.sdu.sesp.geight.common.data.Entity;
import du.sdu.sesp.geight.common.bullet.Bullet;

public abstract class Weapon extends Entity {
    private Bullet bullet;
    private int damage;

    public Bullet getBullet() {
        return bullet;
    }

    public void setBullet(Bullet bullet) {
        this.bullet = bullet;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }
}

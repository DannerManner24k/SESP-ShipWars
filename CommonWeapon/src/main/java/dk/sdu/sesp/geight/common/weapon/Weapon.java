package dk.sdu.sesp.geight.common.weapon;

import dk.sdu.sesp.geight.common.data.Entity;
import du.sdu.sesp.geight.common.bullet.Bullet;

public abstract class Weapon extends Entity {
    private String name;
    private Bullet bullet;

    public String getName() {
        return name;
    }

}

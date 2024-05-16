package dk.sdu.sesp.geight.common.weapon;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.entityparts.IWeapon;
import du.sdu.sesp.geight.common.bullet.Bullet;

public class Weapon implements IWeapon {
    private String name;
    private Bullet bullet;

    public Weapon(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean hasStatusEffect() {
        return false;
    }

    @Override
    public void processStatusEffect(GameData gameData) {

    }
}

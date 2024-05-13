package dk.sdu.sesp.geight.character;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.weapon.Weapon;

public abstract class Character extends Entity{
    protected Weapon weapon;


    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }


}

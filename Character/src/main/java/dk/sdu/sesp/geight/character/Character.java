package dk.sdu.sesp.geight.character;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.weapon.Weapon;

public abstract class Character extends Entity{
    protected int health;
    protected Weapon weapon;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }
}

package dk.sdu.sesp.geight.common.data.entityparts;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;

/**
 *
 * @author Someone
 */
public class LifePart implements EntityPart {

    private boolean dead = false;
    private int life;
    private int maxLife;
    private boolean isHit = false;
    private int damage;

    public LifePart(int life, int maxLife) {
        this.life = life;
        this.maxLife = maxLife;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }
    public int getMaxLife() {
        return maxLife; // Add this line
    }

    public boolean isHit() {
        return isHit;
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }

    public boolean isDead() {
        return dead;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (isHit) {
            life = - damage;
            isHit = false;
        }
        if (life <= 0) {
            dead = true;
        }

    }
}

package dk.sdu.sesp.geight.common.data.entityparts;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;

public class LifePart implements EntityPart {

    private boolean dead = false;
    private int life;
    private int maxLife;
    private boolean isHit = false;
    private int damage;
    private int damageTaken;

    public LifePart(int life, int maxLife) {
        this.life = life;
        this.maxLife = maxLife;
        this.damage = 1;
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

    public void setIsHit(boolean isHit, int damageTaken) {
        this.damageTaken = damageTaken;
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

    public int getDamageTaken() {
        return damageTaken;
    }

    public void setDamageTaken(int damageTaken) {
        this.damageTaken = damageTaken;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (isHit) {
            life -= damageTaken;
            System.out.println("damage taken: " + damageTaken);
            isHit = false;
            System.out.println("life: " + life + " for entity: " + entity.getID());
        }
        if (life <= 0) {
            dead = true;
        }

    }
}

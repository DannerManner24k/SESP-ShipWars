package dk.sdu.sesp.geight.enemysystem;

import dk.sdu.sesp.geight.character.Character;
import dk.sdu.sesp.geight.common.weapon.BurstCanon;
import dk.sdu.sesp.geight.common.weapon.DefaultCanon;
import dk.sdu.sesp.geight.common.weapon.MissileCanon;

public class Enemy extends Character{
    public Enemy() {
        super();
        addWeapon(new DefaultCanon());
        addWeapon(new MissileCanon());
        addWeapon(new BurstCanon());
    }
}

package dk.sdu.sesp.geight.playersystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import dk.sdu.sesp.geight.character.Character;
import dk.sdu.sesp.geight.common.weapon.BurstCanon;
import dk.sdu.sesp.geight.common.weapon.DefaultCanon;
import dk.sdu.sesp.geight.common.weapon.MissileCanon;

public class Player extends Character {
    public Player() {
        super();
        addWeapon(new DefaultCanon());
        addWeapon(new MissileCanon());
        addWeapon(new BurstCanon());
    }
}

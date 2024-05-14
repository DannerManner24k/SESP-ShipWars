package dk.sdu.sesp.geight.weaponsystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;
import dk.sdu.sesp.geight.common.weapon.Weapon;
import du.sdu.sesp.geight.common.bullet.BulletSPI;

import java.util.HashMap;
import java.util.Map;

public class WeaponControlSystem implements IEntityProcessingService {
    private static Map<String, Weapon> weaponTypes = new HashMap<>();
    private boolean isPlayerTurn = true;
    private boolean hasFiredThisTurn = false;

    @Override
    public void process(GameData gameData, World world) {

    }

}

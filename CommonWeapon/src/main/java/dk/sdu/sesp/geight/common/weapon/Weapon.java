package dk.sdu.sesp.geight.common.weapon;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;

public abstract class Weapon extends Entity {
    public abstract void shoot(GameData gameData, World world, Entity shooter, float strength);
}

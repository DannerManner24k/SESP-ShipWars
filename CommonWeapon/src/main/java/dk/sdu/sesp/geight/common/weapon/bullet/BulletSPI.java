package dk.sdu.sesp.geight.common.weapon.bullet;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;

public interface BulletSPI {
    Entity createBullet(Entity entity, GameData gameData, float strength);
}

package du.sdu.sesp.geight.common.bullet;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;

public interface BulletSPI {
    Entity createBullet(Entity entity, GameData gameData);
}

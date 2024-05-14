package dk.sdu.sesp.geight.weaponsystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.services.IGamePluginService;
import dk.sdu.sesp.geight.common.weapon.BurstCanon;
import du.sdu.sesp.geight.common.bullet.Bullet;

public class WeaponPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world, SpriteBatch batch) {

    }

    public void createBurstCanon(GameData gameData, World world, Entity entity) {
        Entity burstCanon = new BurstCanon();
        Bullet bullet = new Bullet();
        ((BurstCanon) burstCanon).setBullet(bullet);
        world.addEntity(burstCanon);
    }

    @Override
    public void stop(GameData gameData, World world, SpriteBatch batch) {

    }
}

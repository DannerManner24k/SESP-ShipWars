package dk.sdu.sesp.geight.weaponsystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.services.IGamePluginService;
import dk.sdu.sesp.geight.common.weapon.BurstCanon;

public class WeaponPlugin implements IGamePluginService {

    @Override
    public void start(GameData gameData, World world, SpriteBatch batch) {

    }

    public void createBurstCanon(GameData gameData, World world, Entity entity) {
        Entity weapon = new BurstCanon();
        world.addEntity(weapon);
    }

    @Override
    public void stop(GameData gameData, World world, SpriteBatch batch) {

    }
}

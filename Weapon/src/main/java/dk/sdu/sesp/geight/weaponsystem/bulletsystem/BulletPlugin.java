package dk.sdu.sesp.geight.weaponsystem.bulletsystem;


import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.services.IGamePluginService;
import dk.sdu.sesp.geight.common.weapon.bullet.Bullet;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class BulletPlugin implements IGamePluginService {

    private Entity bullet;

    @Override
    public void start(GameData gameData, World world, SpriteBatch batch) {

    }

    @Override
    public void stop(GameData gameData, World world, SpriteBatch batch) {
        for (Entity e : world.getEntities()) {
            if (e.getClass() == Bullet.class) {
                world.removeEntity(e);
            }
        }
    }
}

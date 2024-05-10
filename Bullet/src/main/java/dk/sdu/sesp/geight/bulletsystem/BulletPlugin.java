package dk.sdu.sesp.geight.bulletsystem;


import com.badlogic.gdx.graphics.Texture;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.services.IGamePluginService;
import du.sdu.sesp.geight.common.bullet.Bullet;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class BulletPlugin implements IGamePluginService {

    private static List<Bullet> activeBullets = new ArrayList<>();

    @Override
    public void start(GameData gameData, World world, SpriteBatch batch) {

    }

    private Entity createBullet(GameData gameData, World world, SpriteBatch batch) {
        Bullet bullet = new Bullet();
        bullet.setTexture(new Texture("assets/images/bullet.png"));

        bullet.setHeight(bullet.getTexture().getHeight());
        bullet.setWidth(bullet.getTexture().getWidth());

        bullet.setRadius((bullet.getHeight()+bullet.getWidth())/4);

        bullet.setX(100);
        bullet.setY(100);
        world.addEntity(bullet);

        return bullet;
    }

    @Override
    public void stop(GameData gameData, World world, SpriteBatch batch) {

    }
}

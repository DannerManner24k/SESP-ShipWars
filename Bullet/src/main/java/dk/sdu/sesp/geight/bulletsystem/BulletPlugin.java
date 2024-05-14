package dk.sdu.sesp.geight.bulletsystem;


import com.badlogic.gdx.graphics.Texture;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.services.IGamePluginService;
import du.sdu.sesp.geight.common.bullet.Bullet;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import du.sdu.sesp.geight.common.bullet.Vector2D;

import java.util.ArrayList;
import java.util.List;

public class BulletPlugin implements IGamePluginService {

    private static List<Bullet> activeBullets = new ArrayList<>();

    @Override
    public void start(GameData gameData, World world, SpriteBatch batch) {

    }

    public Entity createBullet(float angle, float speed, World world) {
        Bullet bullet = new Bullet();
        bullet.setTexture(new Texture("assets/images/bullet.png"));
        bullet.setHeight(bullet.getTexture().getHeight());
        bullet.setWidth(bullet.getTexture().getWidth());
        bullet.setActive(true);

        // Calculate velocity based on angle and speed
        float radians = (float) Math.toRadians(angle);
        Vector2D velocity = new Vector2D((float) (speed * Math.cos(radians)), (float) (speed * Math.sin(radians)));
        bullet.setVelocity(velocity);
//        float vx = (float) (speed * Math.cos(radians));
//        float vy = (float) (speed * Math.sin(radians));

        world.addEntity(bullet);
        BulletControlSystem.addBullet(bullet);

        return bullet;
    }

    @Override
    public void stop(GameData gameData, World world, SpriteBatch batch) {

    }
}

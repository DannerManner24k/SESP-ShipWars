package dk.sdu.sesp.geight.bulletsystem;


import du.sdu.sesp.geight.common.bullet.Bullet;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;
import java.util.List;

public class BulletPlugin {
    private static List<Bullet> activeBullets = new ArrayList<>();

    public void render(SpriteBatch batch) {
        Bullet bullet = new Bullet(0, 0, 5, 1, 10, 5);
        bullet.render(batch);
        activeBullets.add(bullet);
    }

    public static void spawnBullet(Bullet bullet) {
        activeBullets.add(bullet);
        System.out.println("Bullet spawned:");
    }

    public static void updateBullets() {
        List<Bullet> toRemove = new ArrayList<>();
        for (Bullet bullet : activeBullets) {
            bullet.update();
            if (!bullet.isActive()) {
                toRemove.add(bullet);
            }
        }
        activeBullets.removeAll(toRemove);
    }
}

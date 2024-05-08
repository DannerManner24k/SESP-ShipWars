package dk.sdu.sesp.geight.bulletsystem;


import du.sdu.sesp.geight.common.bullet.Bullet;

import java.util.ArrayList;
import java.util.List;

public class BulletPlugin {
    private static List<Bullet> activeBullets = new ArrayList<>();

    public void initialize() {
        System.out.println("Bullet system initialized.");
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

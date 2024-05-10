package dk.sdu.sesp.geight.bulletsystem;

import dk.sdu.sesp.geight.common.data.Entity;
import du.sdu.sesp.geight.common.bullet.Bullet;

import java.util.ArrayList;
import java.util.List;

public class BulletControlSystem {
    private static List<Bullet> activeBullets = new ArrayList<>();

    // Add a new bullet to the list
    public static void addBullet(Bullet bullet) {
        activeBullets.add(bullet);
        System.out.println("Bullet added: " + bullet);
    }

    // Update each bullet's position and remove inactive ones
    public static void updateBullets() {
        List<Bullet> toRemove = new ArrayList<>();
        for (Bullet bullet : activeBullets) {
            if (!bullet.isActive()) {
                toRemove.add(bullet);
            }
        }
        activeBullets.removeAll(toRemove);
    }

    // Clear all active bullets
    public static void clearBullets() {
        activeBullets.clear();
    }

    // Check collisions between bullets and entities (e.g., players or enemies)
    public static void checkCollisions(List<Entity> entities) {
        for (Bullet bullet : activeBullets) {
            for (Entity entity : entities) {
                /*
                // Example collision detection logic
                if (bullet.getPosition().dst(entity.getPosition()) < bullet.getWidth()) {
                    System.out.println("Bullet hit an entity!");
                }

                 */
            }
        }
    }
}

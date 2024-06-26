package dk.sdu.sesp.geight.common.weapon;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.weapon.bullet.BulletSPI;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class BurstCanon extends Weapon{
    @Override
    public void shoot(GameData gameData, World world, Entity shooter, float strength) {
        for (int i = 1; i < 4; i++) {
            for (BulletSPI bulletSPI : getBulletSPIs()) {
                float newStrength = strength * (1-(i*0.1f));
                world.addEntity(bulletSPI.createBullet(shooter, gameData, newStrength));
                System.out.println("BurstCanon shoot");
            }
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}

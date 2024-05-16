package dk.sdu.sesp.geight.common.weapon;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import du.sdu.sesp.geight.common.bullet.BulletSPI;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class DefaultCanon extends Weapon{
    @Override
    public void shoot(GameData gameData, World world, Entity shooter) {
        for (BulletSPI bulletSPI : getBulletSPIs()) {
            world.addEntity(bulletSPI.createBullet(shooter, gameData));
            System.out.println("DefaultCanon shoot");
        }
    }
    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}

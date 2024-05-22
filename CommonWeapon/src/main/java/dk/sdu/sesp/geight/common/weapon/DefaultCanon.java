package dk.sdu.sesp.geight.common.weapon;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import du.sdu.sesp.geight.common.bullet.Bullet;
import du.sdu.sesp.geight.common.bullet.BulletSPI;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class DefaultCanon extends Weapon{
    private DefaultCanon defaultCanon;
    @Override
    public void shoot(GameData gameData, World world, Entity shooter, float strength) {
        for (BulletSPI bulletSPI : getBulletSPIs()) {
            Bullet bullet = (Bullet) bulletSPI.createBullet(shooter, gameData, strength);
            bullet.setOwner(defaultCanon);
            world.addEntity(bullet);
            System.out.println("DefaultCanon shoot");
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}

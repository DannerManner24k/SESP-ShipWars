package dk.sdu.sesp.geight.weaponsystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.CanonPart;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;
import dk.sdu.sesp.geight.common.weapon.BurstCanon;
import dk.sdu.sesp.geight.common.weapon.DefaultCanon;
import dk.sdu.sesp.geight.common.weapon.MissileCanon;
import du.sdu.sesp.geight.common.bullet.BulletSPI;
import dk.sdu.sesp.geight.character.Character;
import dk.sdu.sesp.geight.playersystem.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class WeaponControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            if (((Character)player).getActivateShot()){
                shoot(player, gameData, world);
                ((Character)player).setActivateShot(false);
            }
        }
    }

    public void shoot(Entity player, GameData gameData, World world) {
        if (((Player)player).getActiveWeapon() instanceof DefaultCanon){
            for (BulletSPI bullet : getBulletSPIs()) {
                world.addEntity(bullet.createBullet(player, gameData));
            }

        } else if (((Player)player).getActiveWeapon() instanceof BurstCanon){
            for (int i = 0; i < 3; i++) {
                int finalI = i;
                for (BulletSPI bullet : getBulletSPIs()) {

                    CanonPart burstCanonPart = player.getPart(CanonPart.class);
                    float radians = burstCanonPart.getRadian();

                    float angleOffset = (float)((finalI - 1) * Math.cos(radians));
                    burstCanonPart.setRadian(radians + angleOffset);

                    world.addEntity(bullet.createBullet(player, gameData));
                }
            }


        } else if (((Player)player).getActiveWeapon() instanceof MissileCanon){
            for (BulletSPI bullet : getBulletSPIs()) {
                world.addEntity(bullet.createBullet(player, gameData));
            }

        } else {
            System.out.println("No weapon selected");
        }
    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}

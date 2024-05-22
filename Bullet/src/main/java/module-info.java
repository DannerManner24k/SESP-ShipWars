import du.sdu.sesp.geight.common.bullet.BulletSPI;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;
import dk.sdu.sesp.geight.common.services.IGamePluginService;

module Bullet {
    exports dk.sdu.sesp.geight.bulletsystem;
    requires CommonBullet;
    requires Common;
    requires com.badlogic.gdx;
    requires CommonWeapon;
    provides BulletSPI with dk.sdu.sesp.geight.bulletsystem.BulletControlSystem;
    provides IEntityProcessingService with dk.sdu.sesp.geight.bulletsystem.BulletControlSystem;
    provides IGamePluginService with dk.sdu.sesp.geight.bulletsystem.BulletPlugin;
}
import dk.sdu.sesp.geight.common.services.IDrawService;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;
import dk.sdu.sesp.geight.common.services.IGamePluginService;
import dk.sdu.sesp.geight.common.weapon.bullet.BulletSPI;

module Weapon {
    exports dk.sdu.sesp.geight.weaponsystem;
    requires Common;
    requires com.badlogic.gdx;
    requires CommonWeapon;
    provides IGamePluginService with dk.sdu.sesp.geight.weaponsystem.WeaponPlugin;
    provides IDrawService with dk.sdu.sesp.geight.weaponsystem.bulletsystem.BulletDrawSystem;
    provides BulletSPI with dk.sdu.sesp.geight.weaponsystem.bulletsystem.BulletControlSystem;
    provides IEntityProcessingService with dk.sdu.sesp.geight.weaponsystem.bulletsystem.BulletControlSystem;
}
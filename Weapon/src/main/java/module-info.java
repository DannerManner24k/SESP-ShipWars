import dk.sdu.sesp.geight.common.services.IEntityProcessingService;
import dk.sdu.sesp.geight.common.services.IGamePluginService;

module Weapon {
    exports dk.sdu.sesp.geight.weaponsystem;
    requires Common;
    requires com.badlogic.gdx;
    requires CommonBullet;
    requires CommonWeapon;
    requires Character;
    requires Player;
    provides IGamePluginService with dk.sdu.sesp.geight.weaponsystem.WeaponPlugin;
}
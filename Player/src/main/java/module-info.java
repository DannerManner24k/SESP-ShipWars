import dk.sdu.sesp.geight.common.services.IGamePluginService;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;

module Player {
    exports dk.sdu.sesp.geight.playersystem;
    requires Character;
    requires com.badlogic.gdx;
    requires Common;
    requires CommonBullet;
    uses Character;
    uses du.sdu.sesp.geight.common.bullet.BulletSPI;
    provides IGamePluginService with dk.sdu.sesp.geight.playersystem.PlayerPlugin;
    provides IEntityProcessingService with dk.sdu.sesp.geight.playersystem.PlayerControlSystem;
}
import dk.sdu.sesp.geight.common.services.IDrawService;
import dk.sdu.sesp.geight.common.services.IGamePluginService;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;

module Player {
    exports dk.sdu.sesp.geight.playersystem;
    requires Character;
    requires com.badlogic.gdx;
    requires Common;
    requires CommonWeapon;
    uses Character;
    provides IGamePluginService with dk.sdu.sesp.geight.playersystem.PlayerPlugin;
    provides IEntityProcessingService with dk.sdu.sesp.geight.playersystem.PlayerControlSystem;
    provides IDrawService with dk.sdu.sesp.geight.playersystem.PlayerDrawSystem;
}
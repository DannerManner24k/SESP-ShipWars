import dk.sdu.sesp.geight.common.services.IDrawService;
import dk.sdu.sesp.geight.common.services.IGamePluginService;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;

module Enemy {
    exports dk.sdu.sesp.geight.enemysystem;
    exports dk.sdu.sesp.geight.enemysystem.ai;
    requires Common;
    requires com.badlogic.gdx;
    requires Character;
    requires CommonWeapon;
    provides IGamePluginService with dk.sdu.sesp.geight.enemysystem.EnemyPlugin;
    provides IEntityProcessingService with dk.sdu.sesp.geight.enemysystem.EnemyControlSystem;
    provides IDrawService with dk.sdu.sesp.geight.enemysystem.EnemyDrawSystem;
}
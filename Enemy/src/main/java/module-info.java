import dk.sdu.sesp.geight.common.services.IGamePluginService;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;

module Enemy {
    exports dk.sdu.sesp.geight.enemysystem;
    exports dk.sdu.sesp.geight.enemysystem.ai;
    requires Common;
    requires com.badlogic.gdx;
    requires Character;
    provides IGamePluginService with dk.sdu.sesp.geight.enemysystem.EnemyPlugin;
    provides IEntityProcessingService with dk.sdu.sesp.geight.enemysystem.EnemyControlSystem;
}
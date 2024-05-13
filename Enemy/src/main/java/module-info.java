import dk.sdu.sesp.geight.common.services.IGamePluginService;

module Enemy {
    exports dk.sdu.sesp.geight.enemysystem;
    requires Common;
    requires com.badlogic.gdx;
    requires Character;
    provides IGamePluginService with dk.sdu.sesp.geight.enemysystem.EnemyPlugin;
}
import dk.sdu.sesp.geight.common.services.IGamePluginService;

module Map {
    exports dk.sdu.sesp.geight.mapsystem;
    requires CommonMap;
    requires com.badlogic.gdx;
    requires Common;
    provides IGamePluginService with dk.sdu.sesp.geight.mapsystem.MapPlugin;
}
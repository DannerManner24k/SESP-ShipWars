module Common {
    requires com.badlogic.gdx;
    exports dk.sdu.sesp.geight.common.data;
    exports dk.sdu.sesp.geight.common.services;
    uses dk.sdu.sesp.geight.common.services.IGamePluginService;
    uses dk.sdu.sesp.geight.common.services.IEntityProcessingService;
    uses dk.sdu.sesp.geight.common.services.IPostEntityProcessingService;
}
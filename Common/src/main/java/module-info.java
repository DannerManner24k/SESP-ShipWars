module Common {
    requires com.badlogic.gdx;
    exports dk.sdu.sesp.geight.common.data;
    exports dk.sdu.sesp.geight.common.services;
    exports dk.sdu.sesp.geight.common.data.entityparts;
    uses dk.sdu.sesp.geight.common.services.IGamePluginService;
    uses dk.sdu.sesp.geight.common.services.IEntityProcessingService;
    uses dk.sdu.sesp.geight.common.services.IPostEntityProcessingService;
    uses dk.sdu.sesp.geight.common.services.IDrawService;
}
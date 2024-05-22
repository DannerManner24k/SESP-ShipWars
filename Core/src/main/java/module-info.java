module Core {
    requires com.badlogic.gdx;
    requires Common;
    requires CommonMap;
    requires Enemy;
    requires Player;
    requires CommonBullet;
    requires CommonWeapon;
    uses dk.sdu.sesp.geight.common.services.IGamePluginService;
    uses dk.sdu.sesp.geight.common.services.IEntityProcessingService;
    uses dk.sdu.sesp.geight.common.services.IPostEntityProcessingService;
}
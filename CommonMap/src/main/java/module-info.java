import dk.sdu.sesp.geight.common.services.ICollidable;
import dk.sdu.sesp.geight.common.services.collision.ITerrain;

module CommonMap {
    requires com.badlogic.gdx;
    requires Common;
    exports dk.sdu.sesp.geight.common.map;
    provides ITerrain with dk.sdu.sesp.geight.common.map.Map;
    provides ICollidable with dk.sdu.sesp.geight.common.map.Map;
}
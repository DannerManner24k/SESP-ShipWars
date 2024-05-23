import dk.sdu.sesp.geight.common.services.ICollidable;
import dk.sdu.sesp.geight.common.services.IPostEntityProcessingService;
import dk.sdu.sesp.geight.common.services.collision.IBullet;
import dk.sdu.sesp.geight.common.services.collision.ITerrain;

module Collision {
    requires Common;
    uses ICollidable;
    uses IBullet;
    uses ITerrain;
    provides IPostEntityProcessingService with dk.sdu.sesp.geight.collisionsystem.CollisionProcessor;
}
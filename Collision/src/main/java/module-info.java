import dk.sdu.sesp.geight.common.services.IPostEntityProcessingService;

module Collision {
    requires Common;
    provides IPostEntityProcessingService with dk.sdu.sesp.geight.collisionsystem.CollisionProcessor;
}
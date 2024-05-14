package dk.sdu.sesp.geight.playersystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.GameKeys;
import dk.sdu.sesp.geight.common.data.entityparts.CanonPart;
import dk.sdu.sesp.geight.common.data.entityparts.LifePart;
import dk.sdu.sesp.geight.common.data.entityparts.MovingPart;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;

public class PlayerControlSystem implements IEntityProcessingService {

    Entity player;

    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            CanonPart canonPart = player.getPart(CanonPart.class);

            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            lifePart.process(gameData, player);
            canonPart.process(gameData, player);

            updateShape(player);
        }
    }


    private void updateShape(Entity entity) {
        float[] shapex = new float[9];
        float[] shapey = new float[9];

        float[] shapeCanonX = new float[6];
        float[] shapeCanonY = new float[6];

        PositionPart positionPart = entity.getPart(PositionPart.class);
        CanonPart canonPart = entity.getPart(CanonPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        shapex[0] = x;
        shapey[0] = y;

        shapex[1] = x + 40;
        shapey[1] = y;

        shapex[2] = x + 16;
        shapey[2] = y - 16;

        shapex[3] = x - 16;
        shapey[3] = y - 16;

        shapex[4] = x - 24;
        shapey[4] = y;

        shapex[5] = x - 16;
        shapey[5] = y;

        shapex[6] = x - 16;
        shapey[6] = y + 16;

        shapex[7] = x;
        shapey[7] = y + 16;

        shapex[8] = x;
        shapey[8] = y;

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);


        // Define the offsets relative to the central point (x, y)
        float CanonX = canonPart.getX();
        float CanonY = canonPart.getY();
        float radians = positionPart.getRadians();
        float halfWidth = 3;
        float height = 20;

        // Top central point (no change in position, just rotation)
        shapeCanonX[0] = x;
        shapeCanonY[0] = y;

        // Top left corner
        shapeCanonX[1] = (float) (CanonX + (-halfWidth) * Math.cos(radians) - (0) * Math.sin(radians));
        shapeCanonY[1] = (float) (y + (-halfWidth) * Math.sin(radians) + (0) * Math.cos(radians));

        // Bottom left corner
        shapeCanonX[2] = (float) (CanonX + (-halfWidth) * Math.cos(radians) - (height) * Math.sin(radians));
        shapeCanonY[2] = (float) (CanonY + (-halfWidth) * Math.sin(radians) + (height) * Math.cos(radians));

        // Bottom right corner
        shapeCanonX[3] = (float) (CanonX + (halfWidth) * Math.cos(radians) - (height) * Math.sin(radians));
        shapeCanonY[3] = (float) (CanonY + (halfWidth) * Math.sin(radians) + (height) * Math.cos(radians));

        // Top right corner
        shapeCanonX[4] = (float) (CanonX + (halfWidth) * Math.cos(radians) - (0) * Math.sin(radians));
        shapeCanonY[4] = (float) (CanonY + (halfWidth) * Math.sin(radians) + (0) * Math.cos(radians));

        // Bottom middle point
        shapeCanonX[5] = (float) (CanonX + (0) * Math.cos(radians) - (height) * Math.sin(radians));
        shapeCanonY[5] = (float) (CanonY + (0) * Math.sin(radians) + (height) * Math.cos(radians));

        // Assign calculated vertices back to the entity
        canonPart.setShapeX(shapeCanonX);
        canonPart.setShapeY(shapeCanonY);
    }

}

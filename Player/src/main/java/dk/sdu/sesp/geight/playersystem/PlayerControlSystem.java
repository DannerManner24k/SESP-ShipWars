package dk.sdu.sesp.geight.playersystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
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
        float radians = positionPart.getRadians();
        float sizeFactor = 1;

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


        shapeCanonX[0] = x;
        shapeCanonY[0] = y;

        shapeCanonX[1] = x - 3;
        shapeCanonY[1] = y;

        shapeCanonX[2] = x - 3;
        shapeCanonY[2] = y + 20;

        shapeCanonX[3] = x + 3;
        shapeCanonY[3] = y + 20;

        shapeCanonX[4] = x + 3;
        shapeCanonY[4] = y;

        shapeCanonX[5] = x;
        shapeCanonY[5] = y;


        canonPart.setShapeCanonX(shapeCanonX);
        canonPart.setShapeCanonY(shapeCanonY);
    }

}

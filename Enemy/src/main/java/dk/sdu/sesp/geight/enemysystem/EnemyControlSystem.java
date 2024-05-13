package dk.sdu.sesp.geight.enemysystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.LifePart;
import dk.sdu.sesp.geight.common.data.entityparts.MovingPart;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;

public class EnemyControlSystem implements IEntityProcessingService {
    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            MovingPart movingPart = enemy.getPart(MovingPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);

            updateShape(enemy);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = new float[9];
        float[] shapey = new float[9];

        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = x;
        shapey[0] = y;

        shapex[1] = x;
        shapey[1] = y+16;

        shapex[2] = x+16;
        shapey[2] = y+16;

        shapex[3] = x+16;
        shapey[3] = y;

        shapex[4] = x+24;
        shapey[4] = y;

        shapex[5] = x+16;
        shapey[5] = y-16;

        shapex[6] = x-16;
        shapey[6] = y-16;

        shapex[7] = x-40;
        shapey[7] = y;

        shapex[8] = x;
        shapey[8] = y;

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}

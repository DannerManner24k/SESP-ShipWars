package dk.sdu.sesp.geight.enemysystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.CanonPart;
import dk.sdu.sesp.geight.common.data.entityparts.LifePart;
import dk.sdu.sesp.geight.common.data.entityparts.MovingPart;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;
import dk.sdu.sesp.geight.common.weapon.BurstCanon;
import dk.sdu.sesp.geight.common.weapon.DefaultCanon;
import dk.sdu.sesp.geight.common.weapon.MissileCanon;
import dk.sdu.sesp.geight.common.weapon.Weapon;

public class EnemyControlSystem implements IEntityProcessingService {

    private Weapon[] weapons;

    public EnemyControlSystem(){
        weapons = new Weapon[]{new DefaultCanon(), new BurstCanon(), new MissileCanon()};
    }
    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);
            CanonPart canonPart = enemy.getPart(CanonPart.class);

            //movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            lifePart.process(gameData, enemy);
            canonPart.process(gameData, enemy);

            updateShape(enemy);
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = new float[9];
        float[] shapey = new float[9];

        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

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

        // Shape of the Canon
        // Drawing shape of the canon to face directly to the right.
        float[] originalX = {0, 0, 20, 20, 0, 0};
        float[] originalY = {0, 3, 3, -3, -3, 0};



        CanonPart canonPart = entity.getPart(CanonPart.class);
        float CanonX = canonPart.getX();
        float CanonY = canonPart.getY();
        float radians = canonPart.getRadian(); // This starts at 0, with the cannon facing right

        float[] shapeCanonX = new float[6];
        float[] shapeCanonY = new float[6];

        // Calculate rotated coordinates
        // Calculate rotated coordinates
        for (int i = 0; i < 6; i++) {
            shapeCanonX[i] = (float) (CanonX + originalX[i] * Math.cos(radians) - originalY[i] * Math.sin(radians));
            shapeCanonY[i] = (float) (CanonY + originalX[i] * Math.sin(radians) + originalY[i] * Math.cos(radians));
        }

        // Assign calculated vertices back to the cannon part
        canonPart.setShapeX(shapeCanonX);
        canonPart.setShapeY(shapeCanonY);
    }
}

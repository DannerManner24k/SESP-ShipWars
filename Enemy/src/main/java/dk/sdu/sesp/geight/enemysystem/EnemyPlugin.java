package dk.sdu.sesp.geight.enemysystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.CanonPart;
import dk.sdu.sesp.geight.common.data.entityparts.LifePart;
import dk.sdu.sesp.geight.common.data.entityparts.MovingPart;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;
import dk.sdu.sesp.geight.common.services.IGamePluginService;

import java.util.Random;

public class EnemyPlugin implements IGamePluginService {
    private Entity enemy;
    @Override
    public void start(GameData gameData, World world, SpriteBatch batch) {
        enemy = createEnemy(gameData, world, batch);
        world.addEntity(enemy);
    }

    private Entity createEnemy(GameData gameData, World world, SpriteBatch batch) {
        Entity enemy = new Enemy();

        float x = 200; //new Random().nextFloat() * gameData.getDisplayWidth();
        float y = 150; //new Random().nextFloat() * gameData.getDisplayHeight();
        float rotationSpeed = 1.5f;
        float radians = 3.1415f / 2;

        enemy.add(new PositionPart(x, y, radians));
        CanonPart canonPart = new CanonPart(x - 10, y, (float) Math.PI);
        enemy.add(canonPart);
        enemy.add(new LifePart(1));
        enemy.setRadius(8);

        float[] shapex = new float[9];
        float[] shapey = new float[9];

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

        enemy.setShapeX(shapex);
        enemy.setShapeY(shapey);

        float[] originalX = {0, 0, 20, 20, 0, 0};
        float[] originalY = {0, 3, 3, -3, -3, 0};


        float CanonX = canonPart.getX();
        float CanonY = canonPart.getY();
        float radiansCanon = canonPart.getRadian(); // This starts at 0, with the cannon facing right

        float[] shapeCanonX = new float[6];
        float[] shapeCanonY = new float[6];


        // Calculate rotated coordinates
        for (int i = 0; i < 6; i++) {
            shapeCanonX[i] = (float) (CanonX + originalX[i] * Math.cos(radiansCanon) - originalY[i] * Math.sin(radiansCanon));
            shapeCanonY[i] = (float) (CanonY + originalX[i] * Math.sin(radiansCanon) + originalY[i] * Math.cos(radiansCanon));
        }

        canonPart.setShapeX(shapeCanonX);
        canonPart.setShapeY(shapeCanonY);

        return enemy;
    }

    @Override
    public void stop(GameData gameData, World world, SpriteBatch batch) {
        world.removeEntity(enemy);
    }
}

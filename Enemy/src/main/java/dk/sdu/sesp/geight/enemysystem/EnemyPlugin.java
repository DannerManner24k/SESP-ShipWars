package dk.sdu.sesp.geight.enemysystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.CanonPart;
import dk.sdu.sesp.geight.common.data.entityparts.LifePart;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;
import dk.sdu.sesp.geight.common.services.IGamePluginService;

import java.util.Random;


public class EnemyPlugin implements IGamePluginService {
    private Entity enemy;
    private final Random rn = new Random();
    @Override
    public int getPriority() {
        return 10;
    }

    @Override
    public void start(GameData gameData, World world, SpriteBatch batch) {
        enemy = createEnemy(gameData, world, batch);
        world.addEntity(enemy);
    }

    private Entity createEnemy(GameData gameData, World world, SpriteBatch batch) {
        Entity enemy = new Enemy();

        int index = world.getSpawnPointsX1().size() - 1;
        double xMin = world.getSpawnPointsX1(index) + 10;
        double xMax = world.getSpawnPointsX2(index) + 10;
        double spawnDifference = xMax - xMin;
        if (spawnDifference <= 20) {
            index = world.getSpawnPointsX1().size() - 2;
            xMin = world.getSpawnPointsX1(index) + 20;
            xMax = world.getSpawnPointsX2(index) - 20;
            spawnDifference = xMax - xMin;
        }


        double spawnX = rn.nextDouble(spawnDifference - 1) + xMin;
        float x = (float) spawnX;
        float y = 350;
        float rotationSpeed = 2;
        float radians = 3.1415f / 2;

        enemy.add(new PositionPart(x, y, radians));
        CanonPart canonPart = new CanonPart(x - 10, y, (float) Math.PI);
        enemy.add(canonPart);
        enemy.add(new LifePart(5,5));
        enemy.setRadius(8);

        world.addEntity(enemy);

        return enemy;
    }

    @Override
    public void stop(GameData gameData, World world, SpriteBatch batch) {
        world.removeEntity(enemy);
    }
}
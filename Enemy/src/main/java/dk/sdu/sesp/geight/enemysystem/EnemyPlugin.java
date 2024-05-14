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

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;

        float x = new Random().nextFloat(gameData.getDisplayWidth()) + (gameData.getDisplayWidth()/2);
        float y = 300;
        float radians = 3.1415f / 2;

        enemy.add(new PositionPart(x, y,radians));
        enemy.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        enemy.add(new CanonPart(x,y, radians));
        enemy.add(new LifePart(1));
        enemy.setRadius(8);

        world.addEntity(enemy);

        return enemy;
    }

    @Override
    public void stop(GameData gameData, World world, SpriteBatch batch) {

    }
}

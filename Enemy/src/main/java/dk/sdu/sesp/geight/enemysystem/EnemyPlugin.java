package dk.sdu.sesp.geight.enemysystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {
    private Entity enemy;
    @Override
    public void start(GameData gameData, World world, SpriteBatch batch) {
        enemy = createEnemy(gameData, world, batch);
        //batch.draw(enemy.getTexture(), enemy.getX(), enemy.getY());
    }

    private Entity createEnemy(GameData gameData, World world, SpriteBatch batch) {
        Entity enemy = new Enemy();

        float[] shapeX = new float[7];  // Increase the number of vertices for complexity
        float[] shapeY = new float[7];

// Define the center of the entity
        float centerX = 400;  // Example: Center of the screen horizontally
        float centerY = 300;  // Example: Center of the screen vertically

// Define vertices relative to the center
        shapeX[0] = centerX;            // Front tip of the ship
        shapeY[0] = centerY - 8;        // Moving up from center

        shapeX[1] = centerX + 6;        // Right side
        shapeY[1] = centerY - 2;        // Slightly above the center

        shapeX[2] = centerX + 8;        // Right bottom
        shapeY[2] = centerY + 8;        // Bottom of the ship

        shapeX[3] = centerX + 4;        // Right rear upper part
        shapeY[3] = centerY + 6;        // Just above the bottom, creating a rear elevation

        shapeX[4] = centerX;            // Center rear, topmost point of the rear
        shapeY[4] = centerY + 8;        // Symmetric to the front but at the back

        shapeX[5] = centerX - 4;        // Symmetric to shapeX[3]
        shapeY[5] = centerY + 6;

        shapeX[6] = centerX - 8;        // Symmetric to shapeX[2]
        shapeY[6] = centerY + 8;


        enemy.setShapeX(shapeX);
        enemy.setShapeY(shapeY);

        //enemy.setX(centerX);
        //enemy.setY(centerY);

        world.addEntity(enemy);

        return enemy;
    }

    @Override
    public void stop(GameData gameData, World world, SpriteBatch batch) {

    }
}

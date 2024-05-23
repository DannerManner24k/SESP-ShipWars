package dk.sdu.sesp.geight.playersystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.character.Character;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.*;
import dk.sdu.sesp.geight.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {
    private Entity player;

    @Override
    public void start(GameData gameData, World world, SpriteBatch batch) {
        player = createPlayer(gameData, world, batch);
        world.addEntity(player);
    }

    public Entity createPlayer(GameData gameData, World world, SpriteBatch batch) {
        player = new Player();
        float rotationSpeed = 1.5f;
        float x = 10;
        float y = 150;
        float radians = 3.1415f / 2;

        player.add(new PositionPart(x, y,radians));
        player.add(new MovingPart(rotationSpeed));
        player.add(new CanonPart(x+10,y, 0)); // +10 is to place the canon in front of the mast
        player.add(new LifePart(1));
        player.setRadius(8);

        float[] shapex = new float[9];
        float[] shapey = new float[9];


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

        player.setShapeX(shapex);
        player.setShapeY(shapey);

        float[] originalX = {0, 0, 20, 20, 0, 0};
        float[] originalY = {0, 3, 3, -3, -3, 0};


        CanonPart canonPart = player.getPart(CanonPart.class);
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

        return player;
    }




    @Override
    public void stop(GameData gameData, World world, SpriteBatch batch) {
        world.removeEntity(player);
    }
}

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
        Entity player = new Player();

        float deacceleration = 10;
        float acceleration = 200;
        float maxSpeed = 300;
        float rotationSpeed = 5;
        float x = 300;
        float y = 200;
        float radians = 3.1415f / 2;

        player.add(new PositionPart(x, y,radians));
        player.add(new MovingPart(deacceleration, acceleration, maxSpeed, rotationSpeed));
        player.add(new CanonPart(x,y, radians));
        player.add(new LifePart(1));
        player.setRadius(8);

        world.addEntity(player);

        return player;
    }


    @Override
    public void stop(GameData gameData, World world, SpriteBatch batch) {

    }
}

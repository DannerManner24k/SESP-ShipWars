package dk.sdu.sesp.geight.playersystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.character.Character;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.CanonPart;
import dk.sdu.sesp.geight.common.data.entityparts.EntityPart;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;
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
        float x = 300;
        float y = 200;
        float radians = 3.1415f / 2;
        player.add(new PositionPart(x, y,radians));
        player.add(new CanonPart());
        player.setRadius(8);

        world.addEntity(player);

        return player;
    }


    @Override
    public void stop(GameData gameData, World world, SpriteBatch batch) {

    }
}

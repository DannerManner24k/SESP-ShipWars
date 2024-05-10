package dk.sdu.sesp.geight.playersystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.character.Character;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {
    private Character player;
    @Override
    public void start(GameData gameData, World world, SpriteBatch batch) {
        player = createPlayer(gameData, world, batch);
        batch.draw(player.getTexture(), player.getPosition().x, player.getPosition().y);
    }

    public Character createPlayer(GameData gameData, World world, SpriteBatch batch) {

        Character player = new Player();
        player.setTexture(new Texture("assets/images/player.png"));

        player.setHeight(player.getTexture().getHeight());
        player.setWidth(player.getTexture().getWidth());

        player.setRadius((player.getHeight()+player.getWidth())/4);

        player.setPosition(100, 100);

        player.setHealth(100);
        world.addEntity(player);

        return player;
    }

    @Override
    public void stop(GameData gameData, World world, SpriteBatch batch) {

    }
}

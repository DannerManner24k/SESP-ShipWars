package dk.sdu.sesp.geight.mapsystem;

import com.badlogic.gdx.graphics.Texture;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.map.Map;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.common.services.IGamePluginService;

public class MapPlugin implements IGamePluginService {
    private Entity map;

    @Override
    public void start(GameData gameData, World world, SpriteBatch batch) {
        map = createMap(gameData, world, batch);
        batch.draw(map.getTexture(), map.getX(), map.getY());
    }

    public Entity createMap(GameData gameData, World world, SpriteBatch batch) {
        Entity map = new Map();
        map.setTexture(new Texture("Map/assets/MapBackground.png"));

        map.setHeight(map.getTexture().getHeight());
        map.setWidth(map.getTexture().getWidth());


        map.setX(0);
        map.setY(0);

        world.addEntity(map);
        return map;
    }

    @Override
    public void stop(GameData gameData, World world, SpriteBatch batch) {

    }
}

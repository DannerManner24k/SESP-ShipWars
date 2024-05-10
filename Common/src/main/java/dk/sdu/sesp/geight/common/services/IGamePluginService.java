package dk.sdu.sesp.geight.common.services;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;

public interface IGamePluginService {
    void start(GameData gameData, World world, SpriteBatch batch);
    void stop(GameData gameData, World world, SpriteBatch batch);
}

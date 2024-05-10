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
        batch.draw(enemy.getTexture(), enemy.getPosition().x, enemy.getPosition().y);
    }

    private Entity createEnemy(GameData gameData, World world, SpriteBatch batch) {
        Entity enemy = new Enemy();
        enemy.setTexture(new Texture("assets/images/enemy.png"));

        enemy.setHeight(enemy.getTexture().getHeight());
        enemy.setWidth(enemy.getTexture().getWidth());

        enemy.setRadius((enemy.getHeight()+enemy.getWidth())/4);

        enemy.setPosition(100, 100);
        world.addEntity(enemy);

        return enemy;
    }

    @Override
    public void stop(GameData gameData, World world, SpriteBatch batch) {

    }
}

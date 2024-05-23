package dk.sdu.sesp.geight.weaponsystem.bulletsystem;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.services.IDrawService;
import dk.sdu.sesp.geight.common.weapon.bullet.Bullet;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;

public class BulletDrawSystem implements IDrawService {
    @Override
    public void draw(ShapeRenderer sr, World world) {
        for (Entity entity : world.getEntities(Bullet.class)){

            PositionPart positionPart = entity.getPart(PositionPart.class);
            sr.begin(ShapeRenderer.ShapeType.Filled);
            sr.setColor(Color.BLACK);
            sr.circle(positionPart.getX(), positionPart.getY(), 3);
            sr.end();
        }
    }

    @Override
    public int getPriority() {
        return 10;
    }
}

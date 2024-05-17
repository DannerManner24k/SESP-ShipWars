package dk.sdu.sesp.geight.enemysystem.ai;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.CanonPart;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;

public class RotateCanonAction implements Action {
    private Entity player;

    public RotateCanonAction(Entity player) {
        this.player = player;
    }

    @Override
    public void execute(Entity enemy, GameData gameData, World world) {
        PositionPart enemyPos = enemy.getPart(PositionPart.class);
        PositionPart playerPos = player.getPart(PositionPart.class);
        CanonPart canonPart = enemy.getPart(CanonPart.class);

        float dx = playerPos.getX() - enemyPos.getX();
        float dy = playerPos.getY() - enemyPos.getY();
        float angle = (float) Math.atan2(dy, dx);

        canonPart.setRadian(angle);
    }
}

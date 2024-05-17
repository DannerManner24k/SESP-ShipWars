package dk.sdu.sesp.geight.enemysystem.ai;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.CanonPart;

public class ShootAtPlayerAction implements Action {
    @Override
    public void execute(Entity enemy, GameData gameData, World world) {
        CanonPart canonPart = enemy.getPart(CanonPart.class);
        // Logic to shoot towards the player
    }
}

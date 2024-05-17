package dk.sdu.sesp.geight.enemysystem.ai;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;

public interface Action {
    void execute(Entity enemy, GameData gameData, World world);
}

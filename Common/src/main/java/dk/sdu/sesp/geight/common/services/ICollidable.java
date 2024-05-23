package dk.sdu.sesp.geight.common.services;

import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;

public interface ICollidable {
    void onCollision(GameData gameData, World world, ICollidable other);
}


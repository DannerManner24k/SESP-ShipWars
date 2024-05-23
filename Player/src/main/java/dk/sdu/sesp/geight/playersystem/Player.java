package dk.sdu.sesp.geight.playersystem;

import dk.sdu.sesp.geight.character.Character;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.services.ICollidable;


public class Player extends Character implements ICollidable {
    @Override
    public void onCollision(GameData gameData, World world, ICollidable other) {
        System.out.println("Player collided with something");
    }
}

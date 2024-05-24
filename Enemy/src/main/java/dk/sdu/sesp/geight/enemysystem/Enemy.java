package dk.sdu.sesp.geight.enemysystem;

import dk.sdu.sesp.geight.character.Character;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.LifePart;
import dk.sdu.sesp.geight.common.services.ICollidable;

public class Enemy extends Character implements ICollidable {
    @Override
    public void onCollision(GameData gameData, World world, ICollidable entity, ICollidable otherEntity) {
        Entity enemy = (Entity) entity;
        Entity other = (Entity) otherEntity;
        LifePart lifePart = enemy.getPart(LifePart.class);
        LifePart otherLifePart = other.getPart(LifePart.class);
        lifePart.setIsHit(true, otherLifePart.getDamage());
    }
}

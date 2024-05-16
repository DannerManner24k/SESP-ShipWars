package dk.sdu.sesp.geight.common.data.entityparts;

import dk.sdu.sesp.geight.common.data.GameData;

public interface IWeapon {
    String getName();
    void setName(String name);

    boolean hasStatusEffect();

    void processStatusEffect(GameData gameData);
}

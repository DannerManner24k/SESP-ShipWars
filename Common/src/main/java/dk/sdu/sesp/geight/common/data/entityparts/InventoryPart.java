package dk.sdu.sesp.geight.common.data.entityparts;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;

import java.util.ArrayList;
import java.util.List;

public class InventoryPart implements EntityPart  {
    private List<IWeapon> weapons;


    public InventoryPart() {
        this.weapons = new ArrayList<>();
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        // Process inventory-related logic if needed
    }

    public void addWeapon(IWeapon weapon) {
        this.weapons.add(weapon);
    }

    public void removeWeapon(IWeapon weapon) {
        this.weapons.remove(weapon);
    }

    public List<IWeapon> getWeapons() {
        return weapons;
    }

    public void setWeapons(List<IWeapon> weapons) {
        this.weapons = weapons;
    }
}

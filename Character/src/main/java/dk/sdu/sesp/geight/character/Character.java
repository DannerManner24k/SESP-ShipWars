package dk.sdu.sesp.geight.character;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.weapon.Weapon;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Character extends Entity{
    private List<Weapon> inventory;
    private Weapon activeWeapon;
    boolean activateShot = false;

    public Character() {
        this.inventory = new ArrayList<>();
        this.activeWeapon = null;
    }

    public void addWeapon(Weapon weapon) {
        if (inventory.size() < 3) {
            inventory.add(weapon);
        }
    }

    public void activateWeapon(Class<? extends Weapon> weaponClass) {
        for (Weapon weapon : inventory) {
            if (weaponClass.isInstance(weapon)) {
                if (activeWeapon != null) {
                    activeWeapon.deactivate();
                }
                activeWeapon = weapon;
                activeWeapon.activate();
                return;
            }
        }
        System.out.println("Weapon not found in inventory: " + weaponClass.getSimpleName());
    }

    public Weapon getActiveWeapon() {
        return activeWeapon;
    }

    public boolean getActivateShot() {
        return activateShot;
    }

    public void setActivateShot(boolean activateShot) {
        this.activateShot = activateShot;
    }
}

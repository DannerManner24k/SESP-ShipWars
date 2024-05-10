package dk.sdu.sesp.geight.weaponsystem;

import dk.sdu.sesp.geight.common.weapon.Weapon;

import java.util.HashMap;
import java.util.Map;

public class WeaponControlSystem {
    private static Map<String, Weapon> weaponTypes = new HashMap<>();
    private boolean isPlayerTurn = true;
    private boolean hasFiredThisTurn = false;

    public static void registerWeaponType(Weapon weapon) {
        weaponTypes.put(weapon.getName(), weapon);
    }

    public void startTurn(boolean isPlayerTurn) {
        this.isPlayerTurn = isPlayerTurn;
        this.hasFiredThisTurn = false;
    }

    public void fireWeapon(String weaponName) {
        if (!hasFiredThisTurn) {
            Weapon weapon = weaponTypes.get(weaponName);
            if (weapon != null && weapon.getCanFire()) {
                weapon.fire();
                hasFiredThisTurn = true;
                System.out.println((isPlayerTurn ? "Player" : "Enemy") + " fired a " + weaponName);
            }
        } else {
            System.out.println("Already fired this turn.");
        }
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public void endTurn() {
        // End the current turn and switch control to the opponent
        startTurn(!isPlayerTurn);
    }
}

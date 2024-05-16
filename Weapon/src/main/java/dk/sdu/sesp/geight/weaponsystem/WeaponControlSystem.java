package dk.sdu.sesp.geight.weaponsystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.IWeapon;
import dk.sdu.sesp.geight.common.data.entityparts.InventoryPart;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;
import dk.sdu.sesp.geight.common.weapon.Weapon;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WeaponControlSystem implements IEntityProcessingService {
    private static Map<String, Weapon> weaponTypes = new HashMap<>();
    private boolean isPlayerTurn = true;
    private boolean hasFiredThisTurn = false;
    private InventoryPart inventory;

    public WeaponControlSystem(InventoryPart inventory) {
        this.inventory = inventory;
    }



    public void addWeaponToInventory(World world, String entityID, String weaponName) {
        Entity entity = world.getEntity(entityID);
        if (entity != null) {
            InventoryPart inventory = entity.getPart(InventoryPart.class);
            if (inventory != null) {
                Weapon weapon = weaponTypes.get(weaponName);
                if (weapon != null) {
                    inventory.addWeapon(weapon);
                }
            }
        }
    }
    public static void registerWeapon(Weapon weapon) {
        weaponTypes.put(weapon.getName(),weapon);
    }

    public void removeWeaponFromInventory(World world, String entityID, String weaponName) {
        Entity entity = world.getEntity(entityID);
        if (entity != null) {
            InventoryPart inventory = entity.getPart(InventoryPart.class);
            if (inventory != null) {
                Weapon weapon = weaponTypes.get(weaponName);
                if (weapon != null) {
                    inventory.removeWeapon(weapon);
                }
            }
        }
    }


    public Weapon getWeaponFromInventory(World inventory, String weaponName) {
        for (IWeapon weapon : inventory.getWeapons()) {
            if (weapon.getName().equals(weaponName)) {
                return (Weapon) weapon;
            }
        }
        return null;
    }


    public static void registerWeaponType(Weapon weapon) {
        weaponTypes.put(weapon.getName(), weapon);
    }

    public void startTurn(boolean isPlayerTurn) {
        this.isPlayerTurn = isPlayerTurn;
        this.hasFiredThisTurn = false;
    }

    public void fireWeapon(String weaponName) {
        /*if (!hasFiredThisTurn) {
            Weapon weapon = weaponTypes.get(weaponName);
            if (weapon != null && weapon.getCanFire()) {
                weapon.fire();
                hasFiredThisTurn = true;
                System.out.println((isPlayerTurn ? "Player" : "Enemy") + " fired a " + weaponName);
            }
        } else {
            System.out.println("Already fired this turn.");
        }

         */
    }

    public boolean isPlayerTurn() {
        return isPlayerTurn;
    }

    public void endTurn() {
        // End the current turn and switch control to the opponent
        startTurn(!isPlayerTurn);
    }

    @Override
    public void process(GameData gameData, World world) {
        // Process all entities that have an InventoryPart
        List<Entity> entitiesWithInventory = world.getEntities(InventoryPart.class);
        for (Entity entity : entitiesWithInventory) {
            InventoryPart inventory = entity.getPart(InventoryPart.class);
            if (inventory != null) {
                inventory.process(gameData, entity);
                processWeaponsInInventory(inventory, gameData);
            }
        }
    }

    private void processWeaponsInInventory(InventoryPart inventory, GameData gameData) {
        for (IWeapon weapon : inventory.getWeapons()) {
            if (weapon.hasStatusEffect()) {
                weapon.processStatusEffect(gameData);
            }
        }
    }


}

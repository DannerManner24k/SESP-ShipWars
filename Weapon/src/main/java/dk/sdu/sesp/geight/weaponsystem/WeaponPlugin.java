package dk.sdu.sesp.geight.weaponsystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.IWeapon;
import dk.sdu.sesp.geight.common.data.entityparts.InventoryPart;
import dk.sdu.sesp.geight.common.services.IGamePluginService;
import dk.sdu.sesp.geight.common.weapon.Weapon;

import static dk.sdu.sesp.geight.weaponsystem.WeaponControlSystem.registerWeaponType;

public class WeaponPlugin implements IGamePluginService {

    private Entity weapon;
    @Override
    public void start(GameData gameData, World world, SpriteBatch batch) {
        Weapon cannon = new Weapon("Cannon");
        WeaponControlSystem.registerWeaponType(cannon);

        // Create an entity and add InventoryPart to it
        Entity weapon = new Entity();
        InventoryPart inventoryPart = new InventoryPart();
        weapon.add(inventoryPart);
        world.addEntity(weapon);

        // Add weapon to player's inventory
        WeaponControlSystem weaponControlSystem = new WeaponControlSystem();
        weaponControlSystem.addWeaponToInventory(world, weapon.getID(), "");

        weaponControlSystem.removeWeaponFromInventory(world, weapon.getID(), "");

        Weapon retrievedWeapon = weaponControlSystem.getWeaponFromInventory(world, weapon.getID());
        if (retrievedWeapon != null) {
            System.out.println("Retrieved weapon: " + retrievedWeapon.getName());
        } else {
            System.out.println("Weapon not found in inventory.");
        }
    }

    @Override
    public void stop(GameData gameData, World world, SpriteBatch batch) {
        world.removeEntity(weapon);
    }
}

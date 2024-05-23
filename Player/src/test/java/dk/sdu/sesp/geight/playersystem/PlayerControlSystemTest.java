package dk.sdu.sesp.geight.playersystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.GameKeys;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.CanonPart;
import dk.sdu.sesp.geight.common.data.entityparts.LifePart;
import dk.sdu.sesp.geight.common.data.entityparts.MovingPart;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerControlSystemTest {

    private PlayerControlSystem playerControlSystem;
    private GameData gameData;
    private World world;
    private Entity player;
    private CanonPart canonPart;
    private MovingPart movingPart;
    private PositionPart positionPart;
    private LifePart lifePart;

    @BeforeEach
    void setUp() {
        playerControlSystem = new PlayerControlSystem();
        gameData = new GameData();
        world = new World();
        player = new Player();

        canonPart = new CanonPart(0, 0, 0);
        movingPart = new MovingPart(1.5f);
        positionPart = new PositionPart(10, 150, 3.1415f / 2);
        lifePart = new LifePart(100, 100);

        player.add(canonPart);
        player.add(movingPart);
        player.add(positionPart);
        player.add(lifePart);

        world.addEntity(player);
    }

    @Test
    void testProcess() {
        gameData.getKeys().setKey(GameKeys.LEFT, true);
        gameData.getKeys().setKey(GameKeys.RIGHT, false);
        gameData.getKeys().update();

        playerControlSystem.process(gameData, world);

        assertTrue(movingPart.isLeft());
        assertFalse(movingPart.isRight());
    }

    @Test
    void testWeaponChange() {
        gameData.getKeys().update();
        gameData.getKeys().setKey(GameKeys.NUM2, true);
        gameData.getKeys().update();
        playerControlSystem.process(gameData, world);
        assertEquals(1, canonPart.getCurrentWeaponIndex());
        /*
        gameData.getKeys().setKey(GameKeys.NUM1, false);
        gameData.getKeys().setKey(GameKeys.NUM2, true);
        gameData.getKeys().update();
        playerControlSystem.process(gameData, world);

        assertEquals(1, canonPart.getCurrentWeaponIndex());

        gameData.getKeys().setKey(GameKeys.NUM2, false);
        gameData.getKeys().setKey(GameKeys.NUM3, true);
        gameData.getKeys().update();
        playerControlSystem.process(gameData, world);

        assertEquals(2, canonPart.getCurrentWeaponIndex());

         */
    }

    @Test
    void testChargingWeapon() {
        gameData.getKeys().setKey(GameKeys.SPACE, true);
        gameData.getKeys().update();
        playerControlSystem.process(gameData, world);

        assertTrue(canonPart.isCharging());
        /*
        gameData.getKeys().setKey(GameKeys.SPACE, false);
        gameData.getKeys().update();
        playerControlSystem.process(gameData, world);

        assertFalse(canonPart.isCharging());
        assertEquals(0, canonPart.getCharge());

         */
    }

    // Add more tests to cover different scenarios and methods
}

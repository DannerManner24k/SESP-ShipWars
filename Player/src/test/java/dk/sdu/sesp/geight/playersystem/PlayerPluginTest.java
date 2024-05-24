package dk.sdu.sesp.geight.playersystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.CanonPart;
import dk.sdu.sesp.geight.common.data.entityparts.LifePart;
import dk.sdu.sesp.geight.common.data.entityparts.MovingPart;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PlayerPluginTest {

    private PlayerPlugin playerPlugin;
    private GameData gameData;
    private World world;

    @BeforeEach
    void setUp() {
        playerPlugin = new PlayerPlugin();
        gameData = new GameData();
        world = new World();
    }

    @Test
    void testStart() {
        playerPlugin.start(gameData, world, null);

        boolean playerExists = false;
        for (Entity entity : world.getEntities(Player.class)) {
            playerExists = true;
            break;
        }
        assertTrue(playerExists);
    }

    @Test
    void testStop() {
        playerPlugin.start(gameData, world, null);
        playerPlugin.stop(gameData, world, null);

        boolean playerExists = false;
        for (Entity entity : world.getEntities(Player.class)) {
            playerExists = true;
            break;
        }
        assertFalse(playerExists);
    }

    @Test
    void testCreatePlayer() {
        Entity player = playerPlugin.createPlayer(gameData, world, null);

        // Verify that the player has the correct components
        assertNotNull(player.getPart(PositionPart.class));
        assertNotNull(player.getPart(MovingPart.class));
        assertNotNull(player.getPart(CanonPart.class));
        assertNotNull(player.getPart(LifePart.class));

        // Verify the initial state of the components
        PositionPart positionPart = player.getPart(PositionPart.class);
        assertEquals(10, positionPart.getX());
        assertEquals(150, positionPart.getY());

        MovingPart movingPart = player.getPart(MovingPart.class);
        assertEquals(1.5f, movingPart.getRotationSpeed());

        CanonPart canonPart = player.getPart(CanonPart.class);
        assertEquals(20, canonPart.getX());
        assertEquals(150, canonPart.getY());

        LifePart lifePart = player.getPart(LifePart.class);
        assertEquals(100, lifePart.getLife());
        assertEquals(100, lifePart.getMaxLife());
    }
}

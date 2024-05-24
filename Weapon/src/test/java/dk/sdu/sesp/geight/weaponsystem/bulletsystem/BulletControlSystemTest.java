package dk.sdu.sesp.geight.weaponsystem.bulletsystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.CanonPart;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;
import dk.sdu.sesp.geight.common.data.entityparts.TimerPart;
import dk.sdu.sesp.geight.common.weapon.bullet.Bullet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BulletControlSystemTest {

    private BulletControlSystem bulletControlSystem;
    private GameData gameData;
    private World world;
    private Entity player;

    @BeforeEach
    void setUp() {
        bulletControlSystem = new BulletControlSystem();
        gameData = new GameData();
        world = new World();
        player = new Entity();

        player.add(new CanonPart(0, 0, 0));
    }

    @Test
    void testCreateBullet() {
        float strength = 50f;
        Bullet bullet = (Bullet) bulletControlSystem.createBullet(player, gameData, strength);

        PositionPart positionPart = bullet.getPart(PositionPart.class);
        assertNotNull(positionPart);

        assertEquals(2, bullet.getRadius());
        assertEquals(strength, bullet.getStrength());
    }

    @Test
    void testProcess() {
        Bullet bullet = (Bullet) bulletControlSystem.createBullet(player, gameData, 50);
        world.addEntity(bullet);

        // Simulate a few game updates
        for (int i = 0; i < 5; i++) {
            bulletControlSystem.process(gameData, world);
        }

        assertTrue(world.getEntities(Bullet.class).contains(bullet));
    }
}

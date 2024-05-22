package dk.sdu.sesp.geight.enemysystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.CanonPart;
import dk.sdu.sesp.geight.common.data.entityparts.LifePart;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;
import dk.sdu.sesp.geight.common.weapon.BurstCanon;
import dk.sdu.sesp.geight.common.weapon.DefaultCanon;
import dk.sdu.sesp.geight.common.weapon.MissileCanon;
import dk.sdu.sesp.geight.common.weapon.Weapon;
import dk.sdu.sesp.geight.enemysystem.ai.EnemyAI;

public class EnemyControlSystem implements IEntityProcessingService {

    private Weapon[] weapons;
    private EnemyAI enemyAI;
    private static final long COOLDOWN_PERIOD = 5000; // 5 seconds cooldown
    private int accuracyLevel = 0; // Default accuracy level (0: least accurate, 4: most accurate)

    public EnemyControlSystem() {
        weapons = new Weapon[]{new DefaultCanon(), new BurstCanon(), new MissileCanon()};
        enemyAI = new EnemyAI();
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);
            CanonPart canonPart = enemy.getPart(CanonPart.class);

            positionPart.process(gameData, enemy);
            lifePart.process(gameData, enemy);
            canonPart.process(gameData, enemy);

            updateShape(enemy);

            long currentTime = System.currentTimeMillis();
            if (currentTime - canonPart.getLastShotTime() >= COOLDOWN_PERIOD) {
                // Recalculate the best shot every time before firing
                float[] aimData = enemyAI.calculateBestShot(enemy, gameData, world, accuracyLevel);
                if (aimData != null) {
                    System.out.println("Enemy: recalculating shot"); // Print statement for recalculating shot
                    handleFiring(gameData, world, enemy, canonPart, aimData);
                    canonPart.setLastShotTime(currentTime); // Reset cooldown
                }
            }
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = new float[9];
        float[] shapey = new float[9];

        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        shapex[0] = x;
        shapey[0] = y;

        shapex[1] = x;
        shapey[1] = y + 16;

        shapex[2] = x + 16;
        shapey[2] = y + 16;

        shapex[3] = x + 16;
        shapey[3] = y;

        shapex[4] = x + 24;
        shapey[4] = y;

        shapex[5] = x + 16;
        shapey[5] = y - 16;

        shapex[6] = x - 16;
        shapey[6] = y - 16;

        shapex[7] = x - 40;
        shapey[7] = y;

        shapex[8] = x;
        shapey[8] = y;

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);

        // Shape of the Canon
        float[] originalX = {0, 0, 20, 20, 0, 0};
        float[] originalY = {0, 3, 3, -3, -3, 0};

        CanonPart canonPart = entity.getPart(CanonPart.class);
        float CanonX = canonPart.getX();
        float CanonY = canonPart.getY();
        float radians = canonPart.getRadian();

        float[] shapeCanonX = new float[6];
        float[] shapeCanonY = new float[6];

        for (int i = 0; i < 6; i++) {
            shapeCanonX[i] = (float) (CanonX + originalX[i] * Math.cos(radians) - originalY[i] * Math.sin(radians));
            shapeCanonY[i] = (float) (CanonY + originalX[i] * Math.sin(radians) + originalY[i] * Math.cos(radians));
        }

        canonPart.setShapeX(shapeCanonX);
        canonPart.setShapeY(shapeCanonY);
    }

    private void handleFiring(GameData gameData, World world, Entity enemy, CanonPart canonPart, float[] aimData) {
        // Print statement for recalculating shot
        System.out.println("Enemy: recalculating shot");

        // Recalculate shot before firing
        aimData = enemyAI.calculateBestShot(enemy, gameData, world, accuracyLevel);

        canonPart.setRadian(aimData[0]);
        canonPart.setCharge((int) aimData[1]); // aimData[1] now contains the calculated power
        canonPart.setCharging(false);

        float canonRadian = canonPart.getRadian();
        float[] lastShotX = new float[2];
        float[] lastShotY = new float[2];
        for (int i = 0; i < 2; i++) {
            int length = i * 15;
            lastShotX[i] = (float) (canonPart.getX() + (25 + length) * Math.cos(canonRadian));
            lastShotY[i] = (float) (canonPart.getY() + (25 + length) * Math.sin(canonRadian));
        }
        canonPart.setLastShotX(lastShotX);
        canonPart.setLastShotY(lastShotY);
        int strength = canonPart.getCharge();
        weapons[canonPart.getCurrentWeaponIndex()].shoot(gameData, world, enemy, (float) strength);
        canonPart.setCharge(0); // Reset charge for next cycle
        canonPart.setChargingUp(true); // Reset direction for next cycle
    }
}

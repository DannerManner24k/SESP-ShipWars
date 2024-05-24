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
import dk.sdu.sesp.geight.common.managers.TurnManager;
import dk.sdu.sesp.geight.common.managers.DifficultyManager;

public class EnemyControlSystem implements IEntityProcessingService {

    private Weapon[] weapons;
    private EnemyAI enemyAI;
    private static final long COOLDOWN_PERIOD = 5000; // 5 seconds cooldown
    private int accuracyLevel; // Default accuracy level (0: least accurate, 4: most accurate)
    private TurnManager turnManager;
    private DifficultyManager difficultyManager;
    private long lastShotTime;

    public EnemyControlSystem() {
        weapons = new Weapon[]{new DefaultCanon(), new BurstCanon(), new MissileCanon()};
        enemyAI = new EnemyAI();
        turnManager = TurnManager.getInstance();
        difficultyManager = DifficultyManager.getInstance();
        lastShotTime = 0;
    }

    private static long enemyTurnStartTime = -1;




    public static void setEnemyTurnStartTime() {
        enemyTurnStartTime = System.currentTimeMillis();
    }


    @Override
    public void process(GameData gameData, World world) {
        accuracyLevel = difficultyManager.getCurrentDifficultyLevel();

        if (!turnManager.isAITurn()) {
            // Reset turn start time if it's not the AI's turn
            enemyTurnStartTime = -1;
            return; // Exit if it's not the AI's turn
        }

        long currentTime = System.currentTimeMillis();

        if (enemyTurnStartTime == -1) {
            // Set the turn start time when it becomes the enemy's turn
            enemyTurnStartTime = currentTime;
        }

        if (currentTime - enemyTurnStartTime < 4000) {
            return; // Exit if 4 seconds delay has not passed
        }

        if (currentTime - lastShotTime < COOLDOWN_PERIOD) {
            return; // Exit if cooldown period has not passed
        }

        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            LifePart lifePart = enemy.getPart(LifePart.class);
            CanonPart canonPart = enemy.getPart(CanonPart.class);

            positionPart.process(gameData, enemy);
            lifePart.process(gameData, enemy);
            canonPart.process(gameData, enemy);

            updateShape(enemy);

            float[] aimData = enemyAI.calculateBestShot(enemy, gameData, world, accuracyLevel);
            if (aimData != null) {
                handleFiring(gameData, world, enemy, canonPart, aimData);
                lastShotTime = currentTime; // Reset cooldown

                // End the AI's turn
                turnManager.nextTurn();
            }
        }
    }



    private void updateShape(Entity entity) {
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

        // Logging firing details
        System.out.println("EnemyControlSystem: Firing with angle=" + aimData[0] + ", power=" + aimData[1]);
    }
}

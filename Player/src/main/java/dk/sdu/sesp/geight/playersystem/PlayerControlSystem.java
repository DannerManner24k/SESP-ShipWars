package dk.sdu.sesp.geight.playersystem;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.GameKeys;
import dk.sdu.sesp.geight.common.data.entityparts.CanonPart;
import dk.sdu.sesp.geight.common.data.entityparts.LifePart;
import dk.sdu.sesp.geight.common.data.entityparts.MovingPart;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;
import dk.sdu.sesp.geight.common.services.IEntityProcessingService;
import dk.sdu.sesp.geight.common.weapon.BurstCanon;
import dk.sdu.sesp.geight.common.weapon.DefaultCanon;
import dk.sdu.sesp.geight.common.weapon.MissileCanon;
import dk.sdu.sesp.geight.common.weapon.Weapon;
import dk.sdu.sesp.geight.common.managers.TurnManager;


public class PlayerControlSystem implements IEntityProcessingService {

    private Weapon[] weapons;
    private TurnManager turnManager;

    public PlayerControlSystem() {
        weapons = new Weapon[]{new DefaultCanon(), new BurstCanon(), new MissileCanon()};
        turnManager = TurnManager.getInstance();
    }

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            CanonPart canonPart = player.getPart(CanonPart.class);

            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            lifePart.process(gameData, player);
            canonPart.process(gameData, player);

            updateShape(player, canonPart);

            if (gameData.getKeys().isPressed(GameKeys.NUM1)) {
                System.out.println("NUM1");
                canonPart.setCurrentWeaponIndex(0);
            } else if (gameData.getKeys().isPressed(GameKeys.NUM2)) {
                System.out.println("NUM2");
                canonPart.setCurrentWeaponIndex(1);
            } else if (gameData.getKeys().isPressed(GameKeys.NUM3)) {
                System.out.println("NUM3");
                canonPart.setCurrentWeaponIndex(2);
            }

            if (gameData.getKeys().isPressed(GameKeys.SPACE)) {
                canonPart.setCharging(true);
                System.out.println("SPACE pressed, isCharging: " + canonPart.isCharging());
            }
            if (gameData.getKeys().isReleased(GameKeys.SPACE)) {
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
                System.out.println("SPACE released, charge: " + strength);
                canonPart.setCharging(false);
                weapons[canonPart.getCurrentWeaponIndex()].shoot(gameData, world, player, (float) strength);
                canonPart.setCharge(0); // Reset charge for next cycle
                canonPart.setChargingUp(true); // Reset direction for next cycle

                // Record the time when the player fires
                gameData.setLastPlayerFireTime(System.currentTimeMillis());

                // End the player's turn
                turnManager.nextTurn();
            }
            canonPart.updateCharge();
        }
    }


    private void updateShape(Entity entity, CanonPart canonPart) {
        float CanonX = canonPart.getX();
        float CanonY = canonPart.getY();
        float radians = canonPart.getRadian(); // This starts at 0, with the cannon facing right

        float[] originalX = {0, 0, 20, 20, 0, 0};
        float[] originalY = {0, 3, 3, -3, -3, 0};

        float[] shapeCanonX = new float[6];
        float[] shapeCanonY = new float[6];


        // Calculate rotated coordinates
        for (int i = 0; i < 6; i++) {
            shapeCanonX[i] = (float) (CanonX + originalX[i] * Math.cos(radians) - originalY[i] * Math.sin(radians));
            shapeCanonY[i] = (float) (CanonY + originalX[i] * Math.sin(radians) + originalY[i] * Math.cos(radians));
        }

        // Assign calculated vertices back to the cannon part
        canonPart.setShapeX(shapeCanonX);
        canonPart.setShapeY(shapeCanonY);

        float canonChargeLength = 20f;
        float chargeLength = canonChargeLength * canonPart.getCharge()/100f;

        float[] chargeBoxX = {0, 0, chargeLength, chargeLength};
        float[] chargeBoxY = {5, 7, 7, 5};

        // Rotated coordinates for the new box shape
        float[] canonChargeX = new float[chargeBoxX.length];
        float[] canonChargeY = new float[chargeBoxY.length];

        // Calculate rotated coordinates for the new box
        for (int i = 0; i < chargeBoxX.length; i++) {
            canonChargeX[i] = (float) (CanonX + chargeBoxX[i] * Math.cos(radians) - chargeBoxY[i] * Math.sin(radians));
            canonChargeY[i] = (float) (CanonY + chargeBoxX[i] * Math.sin(radians) + chargeBoxY[i] * Math.cos(radians));
        }

        canonPart.setChargingShapeX(canonChargeX);
        canonPart.setChargingShapeY(canonChargeY);



        float[] currentX = canonPart.getCurrentShotX();
        float[] currentY = canonPart.getCurrentShotY();
        for (int i = 0; i < 2; i++) {
            int lengthCanon = i * 25;
            currentX[i] = (float) (canonPart.getX() + (25 + lengthCanon) * Math.cos(radians));
            currentY[i] = (float) ( canonPart.getY() + (25 + lengthCanon) * Math.sin(radians));
        }

        canonPart.setCurrentShotX(currentX);
        canonPart.setCurrentShotY(currentY);


    }

}

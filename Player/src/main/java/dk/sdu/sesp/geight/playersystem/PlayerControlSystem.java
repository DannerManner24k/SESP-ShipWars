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
import du.sdu.sesp.geight.common.bullet.Bullet;
import du.sdu.sesp.geight.common.bullet.BulletSPI;

import java.util.Collection;
import java.util.ServiceLoader;

import static java.util.stream.Collectors.toList;

public class PlayerControlSystem implements IEntityProcessingService {

    Entity player;

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

            updateShape(player);

            if (gameData.getKeys().isPressed(GameKeys.SPACE)){
                System.out.println("SPACE");
                ((Player) player).setActivateShot(true);
            }

            if (gameData.getKeys().isPressed(GameKeys.NUM1)){
                System.out.println("NUM1");
                ((Player)player).activateWeapon(DefaultCanon.class);
                System.out.println(((Player)player).getActiveWeapon());
            }

            if (gameData.getKeys().isPressed(GameKeys.NUM2)){
                System.out.println("NUM2");
                ((Player)player).activateWeapon(BurstCanon.class);
                System.out.println(((Player)player).getActiveWeapon());
            }

            if (gameData.getKeys().isPressed(GameKeys.NUM3)){
                System.out.println("NUM3");
                ((Player)player).activateWeapon(MissileCanon.class);
                System.out.println(((Player)player).getActiveWeapon());
            }
        }
    }


    private void updateShape(Entity entity) {
        //Shape of the ship
        float[] shapex = new float[9];
        float[] shapey = new float[9];

        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();

        shapex[0] = x;
        shapey[0] = y;

        shapex[1] = x + 40;
        shapey[1] = y;

        shapex[2] = x + 16;
        shapey[2] = y - 16;

        shapex[3] = x - 16;
        shapey[3] = y - 16;

        shapex[4] = x - 24;
        shapey[4] = y;

        shapex[5] = x - 16;
        shapey[5] = y;

        shapex[6] = x - 16;
        shapey[6] = y + 16;

        shapex[7] = x;
        shapey[7] = y + 16;

        shapex[8] = x;
        shapey[8] = y;

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);

        // Shape of the Canon
        // Drawing shape of the canon to face directly to the right.
        float[] originalX = {0, 0, 20, 20, 0, 0};
        float[] originalY = {0, 3, 3, -3, -3, 0};



        CanonPart canonPart = entity.getPart(CanonPart.class);
        float CanonX = canonPart.getX();
        float CanonY = canonPart.getY();
        float radians = canonPart.getRadian(); // This starts at 0, with the cannon facing right

        float[] shapeCanonX = new float[6];
        float[] shapeCanonY = new float[6];

        // Calculate rotated coordinates
        // Calculate rotated coordinates
        for (int i = 0; i < 6; i++) {
            shapeCanonX[i] = (float) (CanonX + originalX[i] * Math.cos(radians) - originalY[i] * Math.sin(radians));
            shapeCanonY[i] = (float) (CanonY + originalX[i] * Math.sin(radians) + originalY[i] * Math.cos(radians));
        }

        // Assign calculated vertices back to the cannon part
        canonPart.setShapeX(shapeCanonX);
        canonPart.setShapeY(shapeCanonY);

        float[] currentX = canonPart.getCurrentShotX();
        float[] currentY = canonPart.getCurrentShotY();
        for (int i = 0; i < 2; i++) {
            int length = i * 25;
            currentX[i] = (float) (canonPart.getX() + (25 + length) * Math.cos(radians));
            currentY[i] = (float) ( canonPart.getY() + (25 + length) * Math.sin(radians));
        }
        canonPart.setCurrentShotX(currentX);
        canonPart.setCurrentShotY(currentY);

    }

    private Collection<? extends BulletSPI> getBulletSPIs() {
        return ServiceLoader.load(BulletSPI.class).stream().map(ServiceLoader.Provider::get).collect(toList());
    }
}

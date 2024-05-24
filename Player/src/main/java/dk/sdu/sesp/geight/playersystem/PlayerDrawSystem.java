package dk.sdu.sesp.geight.playersystem;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.sesp.geight.common.data.Entity;
import com.badlogic.gdx.Gdx;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.CanonPart;
import dk.sdu.sesp.geight.common.data.entityparts.LifePart;
import dk.sdu.sesp.geight.common.data.entityparts.PositionPart;
import dk.sdu.sesp.geight.common.services.IDrawService;


public class PlayerDrawSystem implements IDrawService {
    private BitmapFont font = new BitmapFont();
    private SpriteBatch batch = new SpriteBatch();
    @Override
    public void draw(ShapeRenderer sr, World world) {
        for (Entity entity : world.getEntities(Player.class)) {

            sr.begin(ShapeRenderer.ShapeType.Filled);
            Color entityColor = Color.GREEN;
            sr.setColor(entityColor);

            float[] shapeX = entity.getShapeX();

            float[] shapeY = entity.getShapeY();

            for (int i = 1; i < shapeX.length - 1; i++) {
                float x1 = shapeX[0], y1 = shapeY[0]; // always the first vertex
                float x2 = shapeX[i], y2 = shapeY[i]; // current vertex
                float x3 = shapeX[i + 1], y3 = shapeY[i + 1]; // next vertex

                sr.triangle(x1, y1, x2, y2, x3, y3);
            }
            sr.line(shapeX[shapeX.length - 1], shapeY[shapeY.length - 1], shapeX[0], shapeY[0]);

            drawCanon(sr, world, entity);

            drawHealthBar(sr, world, entity);

            sr.end();
            drawInventoryUI(sr, world, entity);
        }

    }

    @Override
    public int getDrawPriority() {
        return 10;
    }


    private void drawDottedLine(ShapeRenderer renderer, float x1, float y1, float x2, float y2, float segmentLength, float gapLength) {
        renderer.setColor(Color.GREEN);
        float totalLength = (float) Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1));
        float numSegments = totalLength / (segmentLength + gapLength);

        float dx = (x2 - x1) / numSegments;
        float dy = (y2 - y1) / numSegments;

        for (int i = 0; i < numSegments; i++) {
            float startX = x1 + i * (dx + dx * gapLength / segmentLength);
            float startY = y1 + i * (dy + dy * gapLength / segmentLength);
            float endX = startX + dx;
            float endY = startY + dy;

            renderer.line(startX, startY, endX, endY);
        }
    }

    private void drawCanon (ShapeRenderer sr, World world, Entity entity) {
        sr.setColor(Color.GREEN);
        CanonPart canonPart = entity.getPart(CanonPart.class);
        float[] shapeCanonX = canonPart.getShapeX();
        float[] shapeCanonY = canonPart.getShapeY();

        for (int i = 1; i < shapeCanonX.length - 1; i++) {
            float x1 = shapeCanonX[0], y1 = shapeCanonY[0]; // always the first vertex
            float x2 = shapeCanonX[i], y2 = shapeCanonY[i]; // current vertex
            float x3 = shapeCanonX[i + 1], y3 = shapeCanonY[i + 1]; // next vertex

            sr.triangle(x1, y1, x2, y2, x3, y3);
        }

        float[] chargingX = canonPart.getChargingShapeX();
        float[] chargingY = canonPart.getChargingShapeY();

        if(chargingX != null) {
            for (int i = 1; i < chargingY.length - 1; i++) {
                float x1 = chargingX[0], y1 = chargingY[0]; // always the first vertex
                float x2 = chargingX[i], y2 = chargingY[i]; // current vertex
                float x3 = chargingX[i + 1], y3 = chargingY[i + 1]; // next vertex

                sr.triangle(x1, y1, x2, y2, x3, y3);
            }
        }


        sr.line(canonPart.getCurrentShotX(0), canonPart.getCurrentShotY(0), canonPart.getCurrentShotX(1), canonPart.getCurrentShotY(1));

        drawDottedLine(sr, canonPart.getLastShotX(0), canonPart.getLastShotY(0), canonPart.getLastShotX(1), canonPart.getLastShotY(1), 2, 1);

    }

    private void drawHealthBar(ShapeRenderer sr, World world, Entity entity) {
        LifePart lifePart = entity.getPart(LifePart.class);
        PositionPart positionPart = entity.getPart(PositionPart.class);
        if (lifePart != null) {
            float healthPercentage = (float) lifePart.getLife() / lifePart.getMaxLife();
            float healthBarWidth = 50; // Set width of health bar
            float healthBarHeight = 5; // Set height of health bar
            float healthBarX = positionPart.getX() - healthBarWidth / 2;
            float healthBarY = positionPart.getY() - 30; // Position above the entity

            sr.setColor(Color.RED);
            sr.rect(healthBarX, healthBarY, healthBarWidth, healthBarHeight);
            sr.setColor(Color.GREEN);
            sr.rect(healthBarX, healthBarY, healthBarWidth * healthPercentage, healthBarHeight);
        }
    }

    private void drawInventoryUI(ShapeRenderer sr, World world, Entity entity) {
        CanonPart canonPart = entity.getPart(CanonPart.class);
        batch.begin();
        font.setColor(Color.WHITE);

        float[][] coordinates = {{20, 30}, {60, 30}, {100, 30}};
        for (int i = 0; i < coordinates.length; i++) {
            font.draw(batch, String.valueOf(i + 1), coordinates[i][0], coordinates[i][1]);
        }
        batch.end();

        Gdx.gl.glLineWidth(3);

        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.setColor(Color.WHITE);
        for (int i = 0; i < coordinates.length; i++) {
            sr.rect(coordinates[i][0] - 10, coordinates[i][1] - 20, 30, 30);
        }

        sr.setColor(Color.GREEN);
        sr.rect(coordinates[canonPart.getCurrentWeaponIndex()][0] - 10, coordinates[canonPart.getCurrentWeaponIndex()][1] - 20, 30, 30);
        sr.end();

        Gdx.gl.glLineWidth(1);
    }
}

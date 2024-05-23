package dk.sdu.sesp.geight.playersystem;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.World;
import dk.sdu.sesp.geight.common.data.entityparts.CanonPart;
import dk.sdu.sesp.geight.common.services.IDrawService;


public class PlayerDrawSystem implements IDrawService {
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





        sr.end();
        }
    }
    private void drawDottedLine(ShapeRenderer renderer, float x1, float y1, float x2, float y2, float segmentLength, float gapLength) {
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

}

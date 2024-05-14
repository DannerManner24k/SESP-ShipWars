/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.sdu.sesp.geight.common.data.entityparts;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;

import static java.lang.Math.*;

/**
 *
 * @author Alexander
 */
public class MovingPart
        implements EntityPart {

    private float rotationSpeed;
    private boolean left, right;

    public MovingPart(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }


    public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }


    @Override
    public void process(GameData gameData, Entity entity) {
        CanonPart canonPart = entity.getPart(CanonPart.class);
        float radians = canonPart.getRadian();
        float dt = gameData.getDelta();

        if (right && radians > 0) {  // Rotate clockwise, decrease radians but not below 0
            radians -= rotationSpeed * dt;
            if (radians < 0) {
                radians = 0;  // Prevent going below 0 radians
            }
        }

        if (left && radians < Math.PI / 2) {  // Rotate counterclockwise, increase radians but not above π/2
            radians += rotationSpeed * dt;
            if (radians > Math.PI / 2) {
                radians = (float) (Math.PI / 2);  // Prevent going above π/2 radians
            }
        }

        canonPart.setRadian(radians);
    }

}

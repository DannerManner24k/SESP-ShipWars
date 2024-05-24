package dk.sdu.sesp.geight.common.services;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import dk.sdu.sesp.geight.common.data.World;

public interface IDrawService{
    void draw(ShapeRenderer sr, World world);
}
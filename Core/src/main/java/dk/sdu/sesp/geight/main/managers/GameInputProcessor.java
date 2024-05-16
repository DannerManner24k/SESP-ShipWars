package dk.sdu.sesp.geight.main.managers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.GameKeys;

public class GameInputProcessor extends InputAdapter {

    private final GameData gameData;
    private final TurnManager turnManager;

    public GameInputProcessor(GameData gameData, TurnManager turnManager) {
        this.gameData = gameData;
        this.turnManager = turnManager;
    }


    public boolean keyDown(int k) {
        if (!turnManager.isPlayerTurn()){
            return false;
        }

        if(k == Keys.UP) {
            gameData.getKeys().setKey(GameKeys.UP, true);
        }
        if(k == Keys.LEFT) {
            gameData.getKeys().setKey(GameKeys.LEFT, true);
        }
        if(k == Keys.DOWN) {
            gameData.getKeys().setKey(GameKeys.DOWN, true);
        }
        if(k == Keys.RIGHT) {
            gameData.getKeys().setKey(GameKeys.RIGHT, true);
        }
        if(k == Keys.ENTER) {
            gameData.getKeys().setKey(GameKeys.ENTER, true);
        }
        if(k == Keys.ESCAPE) {
            gameData.getKeys().setKey(GameKeys.ESCAPE, true);
        }
        if(k == Keys.SPACE) {
            gameData.getKeys().setKey(GameKeys.SPACE, true);
        }
        if(k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT) {
            gameData.getKeys().setKey(GameKeys.SHIFT, true);
        }
        if (k == Keys.NUM_1) {
            gameData.getKeys().setKey(GameKeys.NUM1, true);
        }
        if (k == Keys.NUM_2) {
            gameData.getKeys().setKey(GameKeys.NUM2, true);
        }
        if (k == Keys.NUM_3) {
            gameData.getKeys().setKey(GameKeys.NUM3, true);
        }
        return true;
    }

    public boolean keyUp(int k) {
        if (!turnManager.isPlayerTurn()) {
            return false;
        }

        if(k == Keys.UP) {
            gameData.getKeys().setKey(GameKeys.UP, false);
        }
        if(k == Keys.LEFT) {
            gameData.getKeys().setKey(GameKeys.LEFT, false);
        }
        if(k == Keys.DOWN) {
            gameData.getKeys().setKey(GameKeys.DOWN, false);
        }
        if(k == Keys.RIGHT) {
            gameData.getKeys().setKey(GameKeys.RIGHT, false);
        }
        if(k == Keys.ENTER) {
            gameData.getKeys().setKey(GameKeys.ENTER, false);
        }
        if(k == Keys.ESCAPE) {
            gameData.getKeys().setKey(GameKeys.ESCAPE, false);
        }
        if(k == Keys.SPACE) {
            gameData.getKeys().setKey(GameKeys.SPACE, false);
        }
        if(k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT) {
            gameData.getKeys().setKey(GameKeys.SHIFT, false);
        }
        if (k == Keys.NUM_1) {
            gameData.getKeys().setKey(GameKeys.NUM1, false);
        }
        if (k == Keys.NUM_2) {
            gameData.getKeys().setKey(GameKeys.NUM2, false);
        }
        if (k == Keys.NUM_3) {
            gameData.getKeys().setKey(GameKeys.NUM3, false);
        }
        return true;
    }

}
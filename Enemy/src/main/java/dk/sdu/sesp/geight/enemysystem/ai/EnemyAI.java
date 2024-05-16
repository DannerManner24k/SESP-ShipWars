package dk.sdu.sesp.geight.enemysystem.ai;

import com.badlogic.gdx.scenes.scene2d.Action;
import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;

import java.util.ArrayList;
import java.util.List;

public class EnemyAI {
    private UtilityCalculator utilityCalculator = new UtilityCalculator();

    public Action decideNextAction(Entity enemy, GameData gameData, World world) {
        List<Action> possibleActions = generatePossibleActions(enemy, gameData, world);
        Action bestAction = null;
        double maxUtility = -Double.MAX_VALUE;

        for (Action action : possibleActions) {
            double utility = utilityCalculator.calculateUtility(action, enemy, gameData, world);
            if (utility > maxUtility) {
                maxUtility = utility;
                bestAction = action;
            }
        }

        return bestAction;
    }

    private List<Action> generatePossibleActions(Entity enemy, GameData gameData, World world) {
        // Generate possible actions based on current game state
        return new ArrayList<>();
    }
}

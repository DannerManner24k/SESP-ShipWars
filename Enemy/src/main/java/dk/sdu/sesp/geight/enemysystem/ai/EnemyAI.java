package dk.sdu.sesp.geight.enemysystem.ai;

import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.GameData;
import dk.sdu.sesp.geight.common.data.World;

import java.util.ArrayList;
import java.util.List;

public class EnemyAI {
    private UtilityCalculator utilityCalculator = new UtilityCalculator();
    private Entity player;

    public EnemyAI(Entity player) {
        this.player = player;
    }

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
        List<Action> actions = new ArrayList<>();
        actions.add(new RotateCanonAction(player));
        actions.add(new ShootAtPlayerAction());
        return actions;
    }
}

package dk.sdu.sesp.geight.common.managers;


import dk.sdu.sesp.geight.common.data.Entity;
import dk.sdu.sesp.geight.common.data.World;

public class GameLogic {

    private DifficultyManager difficultyManager;
    private TurnManager turnManager;
    private static GameLogic instance;
    private World world;


    public static synchronized GameLogic getInstance(World world) {
        if (instance == null) {
            instance = new GameLogic(world);
        }
        return instance;
    }
    public static synchronized GameLogic getInstance() {
        if (instance == null) {
            throw new IllegalStateException("DifficultyManager is not initialized. Call getInstance(int, int) first.");
        }
        return instance;
    }


    private GameLogic(World world) {
        this.world = world;
        this.difficultyManager = DifficultyManager.getInstance(1, 5); // Initialize singleton instance
        this.turnManager = TurnManager.getInstance();

        System.out.println("ganmelogic");
    }

    private int currentLevel;
    private boolean enemyDead = false;
    private boolean isGameOver = false;

    public void startGame() {
        loadLevel(1);
    }

    public void loadLevel(int level) {
        this.currentLevel = level;
        initializeEntities();
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    private void initializeEntities() {
        difficultyManager.initializeEntities();
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    public void updateGame() {
        if (enemyDead) {
            levelUp();
        }
    }

    public void levelUp() {
        difficultyManager.increaseDifficulty();
        loadLevel(difficultyManager.getCurrentDifficultyLevel());
    }

    public boolean isEnemyDead() {
        return enemyDead;
    }

    public void setEnemyDead(boolean enemyDead) {
        this.enemyDead = enemyDead;
    }
}


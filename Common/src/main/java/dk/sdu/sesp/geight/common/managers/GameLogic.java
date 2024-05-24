package dk.sdu.sesp.geight.common.managers;


public class GameLogic {

    private DifficultyManager difficultyManager;
    private TurnManager turnManager;
    private static GameLogic instance;
    public static synchronized GameLogic getInstance() {
        if (instance == null) {
            instance = new GameLogic();
        }
        return instance;
    }

    private GameLogic() {
        this.difficultyManager = DifficultyManager.getInstance(1, 4); // Initialize singleton instance
        this.turnManager = TurnManager.getInstance();
        System.out.println("ganmelogic");
        // Initialize other components as necessary
    }



    private int currentLevel;
    private int playerScore;
    private boolean isGameOver = false;
    private int life;



    public void startGame() {
        loadLevel(1); // Start with the first level
    }

    private void loadLevel(int level) {
        this.currentLevel = level;
        // Load the level data (map, entities, etc.)
        //this.currentMap = loadMapForLevel(level);
        //this.entities = loadEntitiesForLevel(level);
        //initializeEntities();
        playerScore = 0; // Reset or update score depending on game design
    }

    public void updateGame() {
        if (isPlayerTurn()) {
            handlePlayerTurn();
        } else {
            handleEnemyTurns();
        }
        updateDifficulty();
    }

    private void handlePlayerTurn() {
        // Player turn logic
    }

    private void handleEnemyTurns() {
        // Enemy turn logic
    }

    private boolean isPlayerTurn() {
        // Determine if it is the player's turn
        return turnManager.isPlayerTurn();
    }

    private void updateDifficulty() {
        difficultyManager.adjustDifficulty(playerScore);
    }

    public void playerScored(int points) {
        playerScore += points;
    }

    // Start game

    // Level handling


    // Game over

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean isGameOver) {
        this.isGameOver = isGameOver;
    }

    // Map and entity initialization
}

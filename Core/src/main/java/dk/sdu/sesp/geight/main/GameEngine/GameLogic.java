package dk.sdu.sesp.geight.main.GameEngine;

import dk.sdu.sesp.geight.common.managers.DifficultyManager;
import dk.sdu.sesp.geight.common.managers.TurnManager;
import dk.sdu.sesp.geight.common.managers.*;

public class GameLogic {
    private int currentLevel;
    private int playerScore;
    private DifficultyManager difficultyManager;
    private TurnManager turnManager;


    public GameLogic() {
        this.difficultyManager = new DifficultyManager(1,2);
        this.turnManager = TurnManager.getInstance();
        System.out.println("ganmelogic");
        // Initialize other components as necessary
    }

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

    //Start game

    //Level handling

    //How good is the player. the better the player is the more advanced should the enemy be.

    //Turnbased logic

    //Scoring

    //Map and entity initialization
}


package dk.sdu.sesp.geight.common.managers;

public class TurnManager {
    private boolean playerTurn; // true if it's the player's turn, false if it's the AI's turn
    private static TurnManager instance;

    private TurnManager() {
        playerTurn = true; // Assuming the player starts the game
    }

    public static TurnManager getInstance() {
        if (instance == null) {
            instance = new TurnManager();
        }
        return instance;
    }

    public void nextTurn() {
        playerTurn = !playerTurn; // Toggle between player and AI turns
        if (playerTurn) {
            System.out.println("Player's Turn");
        } else {
            System.out.println("Enemy's Turn");
        }
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public boolean isAITurn() {
        return !playerTurn;
    }
}

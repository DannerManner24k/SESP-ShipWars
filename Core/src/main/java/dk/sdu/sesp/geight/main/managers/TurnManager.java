package dk.sdu.sesp.geight.main.managers;



public class TurnManager {
    private boolean playerTurn; // true if it's the player's turn, false if it's the AI's turn

    public TurnManager() {
        playerTurn = true; // Assuming the player starts the game
    }

    public void nextTurn() {
        playerTurn = !playerTurn; // Toggle between player and AI turns
    }

    public boolean isPlayerTurn() {
        return playerTurn;
    }

    public boolean isAITurn() {
        return !playerTurn;
    }
}

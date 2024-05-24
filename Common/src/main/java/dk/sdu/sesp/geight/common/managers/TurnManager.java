package dk.sdu.sesp.geight.common.managers;

public class TurnManager {
    private boolean playerTurn; // true if it's the player's turn, false if it's the AI's turn
    private static TurnManager instance;
    private boolean safeToShot;

    private TurnManager() {
        playerTurn = true;
        safeToShot = true;// Assuming the player starts the game
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
        return !playerTurn && safeToShot;
    }

    // Method to set safeToShot to true when a bullet has collided with something
    public void bulletCollided() {
        safeToShot = true;
    }


    public void setSafeToShot(boolean b) {
        safeToShot = b;
    }

}

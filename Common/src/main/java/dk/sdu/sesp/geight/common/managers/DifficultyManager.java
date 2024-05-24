package dk.sdu.sesp.geight.common.managers;

public class DifficultyManager {
    private int difficultyLevel;
    private final int maxDifficulty;
    private final int minDifficulty;

    private static DifficultyManager instance;

    private DifficultyManager(int minDifficulty, int maxDifficulty) {
        this.minDifficulty = minDifficulty;
        this.maxDifficulty = maxDifficulty;
        this.difficultyLevel = minDifficulty;  // Start at the lowest difficulty
    }

    public static synchronized DifficultyManager getInstance(int minDifficulty, int maxDifficulty) {
        if (instance == null) {
            instance = new DifficultyManager(minDifficulty, maxDifficulty);
        }
        return instance;
    }

    public static synchronized DifficultyManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("DifficultyManager is not initialized. Call getInstance(int, int) first.");
        }
        return instance;
    }

    public void increaseDifficulty() {
        if (difficultyLevel < maxDifficulty) {
            difficultyLevel++;
        }
    }

    public void decreaseDifficulty() {
        if (difficultyLevel > minDifficulty) {
            difficultyLevel--;
        }
    }

    public void adjustDifficulty(int playerScore) {
        // Example logic to adjust difficulty based on score
        if (playerScore > 1000) {
            setDifficulty(3); // Hard
        } else if (playerScore > 500) {
            setDifficulty(2); // Medium
        } else {
            setDifficulty(1); // Easy
        }
    }

    public int getCurrentDifficultyLevel() {
        return difficultyLevel;
    }

    private void setDifficulty(int newDifficulty) {
        if (newDifficulty >= minDifficulty && newDifficulty <= maxDifficulty) {
            difficultyLevel = newDifficulty;
        }
    }
}

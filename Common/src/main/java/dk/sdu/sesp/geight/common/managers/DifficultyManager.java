package dk.sdu.sesp.geight.common.managers;

public class DifficultyManager {
    private static DifficultyManager instance;
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

    private int difficultyLevel; // start
    private final int maxDifficulty; // 5
    private final int minDifficulty; // 1



    private DifficultyManager(int minDifficulty, int maxDifficulty) {
        this.minDifficulty = minDifficulty;
        this.maxDifficulty = maxDifficulty;
        this.difficultyLevel = minDifficulty;  // Start at the lowest difficulty
    }

    private int enemyLife;

    public void initializeEntities() {
        // Example of scaling enemy life
        enemyLife = 3;
        switch (difficultyLevel) {
            case 1:
                break;
            case 2:
                enemyLife = 5;
                break;
            case 3:
                enemyLife = 7;
                break;
            case 4:
                enemyLife = 9;
                break;
            case 5:
                enemyLife = 15;
                break;
            default:
                enemyLife = 3;
                break;
        }
    }

    public int getEnemyLife() {
        return enemyLife;
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

    public int getCurrentDifficultyLevel() {
        return difficultyLevel;
    }

}

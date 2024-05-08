package dk.sdu.sesp.geight.enemysystem;

import dk.sdu.sesp.geight.character.Character;

public class Enemy extends Character{
    public Enemy(float x, float y) {
        super(x, y, 64, 64, "enemy.png", 100);
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        // Implement AI behaviors
    }

    @Override
    protected void onDeath() {
        System.out.println("Enemy died!");
        // Handle enemy death (increase score, spawn loot, etc.)
    }
}

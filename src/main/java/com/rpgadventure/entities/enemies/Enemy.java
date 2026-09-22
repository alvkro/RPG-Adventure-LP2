package entities.enemies;
import entities.Entity;

public abstract class Enemy extends Entity {
    public Enemy(String name, int coins) {
        super(name, coins);
    }
    public abstract int attack();
}

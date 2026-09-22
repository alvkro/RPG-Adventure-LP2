package entities;
import equipment.*;

public abstract class Entity {
    private String name;
    private int damage;
    private int life;
    private int level;
    private Inventory inventory;
    private int energy;
    private int coins;

    public Entity(String name, int coins) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("entities.Adventurer name cannot be empty or null.");
        }
        if (coins < 0) {
            throw new IllegalArgumentException("Moedas não podem ser negativas.");
        }

        this.name = name;
        this.level = 1;
        this.life = 100;
        this.energy = 0;
        this.damage = 10;
        this.inventory = new Inventory();
        this.coins = coins;
    }

    public void heal(int amount_heal){ this.life += amount_heal; }
    public void recieveDmg(int amount_dmg) { this.life -= amount_dmg; }

    public String getName() {
        return name;
    }
    public int getLife() { return life; }
    public int getLevel() {
        return level;
    }
    public int getCoins() {
        return this.coins;
    }
    public int getEnergy() {
        return this.energy;
    }
    public Inventory getInventory() {
        return inventory;
    }
    public int getDamage() { return damage; }
}

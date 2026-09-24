package entities;

import equipment.*;

public abstract class Entity {
    private String name;
    private int damage;
    private int life;
    private int level;
    private Inventory<Item> inventory;
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
        this.inventory = new Inventory<Item>();
        this.coins = coins;
    }

    public void heal(int amount_heal) {
        this.life += amount_heal;
        if (this.life > 100) {
            this.life = 100;
        }
    }

    public void receiveDmg(int amount_dmg) {
        this.life -= amount_dmg;
        if (this.life < 0) {
            this.life = 0;
        }
    }

    public void addCoins(int amount) {
        this.coins += amount;
    }

    public String getName() {
        return name;
    }

    public int getLife() {
        return life;
    }

    public int getLevel() {
        return level;
    }

    public int getCoins() {
        return this.coins;
    }

    public int getEnergy() {
        return this.energy;
    }

    public Inventory<Item> getInventory() {
        return inventory;
    }

    public int getDamage() {
        return damage;
    }
}

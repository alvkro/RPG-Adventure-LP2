package equipment;

public class Weapon extends Item {
    private final int damage;

    public Weapon(String name, ItemType type, Rarity rarity, int damage) {
        super(name, type, rarity);
        if (damage < 0) {
            throw new IllegalArgumentException("Weapon damage cannot be negative.");
        }
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }
}
package equipment;

public class Potion extends Item implements Usable {
    private final int amount_heal;

    public Potion(String name, ItemType type, Rarity rarity, int amount_heal){
        super(name, type, rarity);
        if (amount_heal < 0) {
            throw new IllegalArgumentException("Potion amount_heal cannot be negative.");
        }
        this.amount_heal = amount_heal;
    }

    @Override
    public void use(Entity entity) {
        entity.heal(amount_heal);
    }
}
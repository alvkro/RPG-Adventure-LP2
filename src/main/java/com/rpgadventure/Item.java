public final class Item {
    private final String name;
    private final ItemType type;
    private final Rarity rarity;

    Item(String name, ItemType type, Rarity rarity) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Item name cannot be empty or null.");
        }
        if (type == null) {
            throw new IllegalArgumentException("Item type cannot be null.");
        }
        if (rarity == null) {
            throw new IllegalArgumentException("Item rarity cannot be null.");
        }

        this.name = name;
        this.type = type;
        this.rarity = rarity;
    }

    public String getName() {
        return name;
    }

    public ItemType getType() {
        return type;
    }

    public Rarity getRarity() {
        return rarity;
    }
}

import java.util.ArrayList;

public class Inventory {
    private final int maxCapacity = 40;
    private ArrayList<Item> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public void insertItem(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Item cannot be null.");

        if (items.size() < maxCapacity)
            items.add(item);
    }

    public void removeItem(Item item) {
        if (item == null)
            throw new IllegalArgumentException("Item cannot be null.");

        if (!items.contains(item))
            throw new IllegalStateException("Item not in inventory.");

        items.remove(item);
    }

    public boolean hasItem(Item item) {
        return items.contains(item);
    }

    @Override
    public String toString() {
        String stream = "";

        for (Item item : items)
            stream += "Item name: " + item.getName() + "\n";

        return stream;
    }
}

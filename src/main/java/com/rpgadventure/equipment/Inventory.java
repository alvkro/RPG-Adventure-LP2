package equipment;

import java.util.HashMap;
import java.util.Map;

public class Inventory<T> {
    private final int maxCapacity = 40;
    private Map<T, Integer> items;

    public Inventory() {
        this.items = new HashMap<>();
    }

    public void insertItem(T item) {
        insertItem(item, 1);
    }

    public void insertItem(T item, Integer quantity) {
        if (item == null)
            throw new IllegalArgumentException("Item cannot be null.");

        if (items.containsKey(item)) {
            int currentQuantity = items.get(item);
            items.put(item, currentQuantity + quantity);
        } else {
            if (items.size() < maxCapacity) {
                items.put(item, quantity);
            } else {
                throw new IllegalStateException("Inventory is full.");
            }
        }
    }

    public void removeItem(T item) {
        if (item == null)
            throw new IllegalArgumentException("Item cannot be null.");

        if (!items.containsKey(item))
            throw new IllegalStateException("Item not in inventory.");

        items.remove(item);
    }

    public void removeItem(T item, Integer quantity) {
        if (item == null)
            throw new IllegalArgumentException("Item cannot be null.");

        if (!items.containsKey(item))
            throw new IllegalStateException("Item not in inventory.");

        int currentQuantity = items.get(item);

        if (quantity > currentQuantity) {
            throw new IllegalStateException("Not enough quantity to remove.");
        } else if (quantity == currentQuantity) {
            items.remove(item);
        } else {
            items.put(item, currentQuantity - quantity);
        }
    }

    public boolean hasItem(T item) {
        return items.containsKey(item);
    }
}

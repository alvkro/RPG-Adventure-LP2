package equipment;

import java.util.HashMap;
import java.util.Map;

public class Inventory<T> {
    private final int maxCapacity = 40;
    private Map<String, T> items;

    public Inventory() {
        this.items = new HashMap<>();
    }

    public void insertItem(T item) {
        if (item == null)
            throw new IllegalArgumentException("equipment.Item cannot be null.");

        if (items.size() < maxCapacity)
            items.put(item.toString(), item);
    }

    public void removeItem(T item) {
        if (item == null)
            throw new IllegalArgumentException("equipment.Item cannot be null.");

        if (!items.containsKey(item.toString()))
            throw new IllegalStateException("equipment.Item not in inventory.");

        items.remove(item.toString());
    }

    public boolean hasItem(T item) {
        return items.containsKey(item.toString());
    }

    @Override
    public String toString() {
        String stream = "";

        for (T item : items.values())
            stream += "equipment.Item name: " + item.toString() + "\n";

        return stream;
    }
}

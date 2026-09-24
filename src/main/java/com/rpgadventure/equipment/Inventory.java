package equipment;

import java.util.ArrayList;
import java.util.List;

public class Inventory<T> {
    private final int maxCapacity = 40;
    private List<T> items;

    public Inventory() {
        this.items = new ArrayList<>();
    }

    public void insertItem(T item) {
        if (item == null)
            throw new IllegalArgumentException("equipment.Item cannot be null.");

        if (items.size() < maxCapacity)
            items.add(item);
    }

    public void removeItem(T item) {
        if (item == null)
            throw new IllegalArgumentException("equipment.Item cannot be null.");

        if (!items.contains(item))
            throw new IllegalStateException("equipment.Item not in inventory.");

        items.remove(item);
    }

    public boolean hasItem(T item) {
        return items.contains(item);
    }

    @Override
    public String toString() {
        String stream = "";

        for (T item : items)
            stream += "equipment.Item name: " + item.toString() + "\n";

        return stream;
    }
}

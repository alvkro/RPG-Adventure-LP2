import java.util.ArrayList;

public class Inventory {
  private final int maxCapacity = 40;
  private ArrayList<Item> items;

  public Inventory() {
    this.items = new ArrayList<>();
  }

  public void insertItem(Item item) {
    if (items.size() < maxCapacity) {
      items.add(item);
    }
  }

  public void removeItem(Item item) {
    if (items.contains(item)) {
      items.remove(item);
    }
  }

  public boolean hasItem(Item item) {
    if (items.contains(item)) {
      return true;
    }

    return false;
  }

  @Override
  public String toString() {
    String stream = "";

    for (Item item : items) {
      stream += "Item name: " + item.getName() + "\n";
    }

    return stream;
  }
}

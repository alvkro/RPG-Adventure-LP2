public class Adventurer {
  private String name;
  private int energy;
  private int level;
  private int coins;
  private Inventory inventory;

  public Adventurer(String name, int coins) {
    this.name = name;
    this.coins = coins;
    this.level = 1;
  }

  public String getName() {
    return name;
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

  public Inventory getInventory() {
    return inventory;
  }

  public void dialogue() {
    System.out.println("Talking...");
  }

  public void receive(Reward r) {
    this.coins += r.getCoin();
  }
}

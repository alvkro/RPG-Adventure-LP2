public class Adventurer {
    private String name;
    private int energy;
    private int level;
    private int coins;
    private Inventory inventory;

    public Adventurer(String name, int coins) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Adventurer name cannot be empty or null.");
        }
        if (coins < 0) {
            throw new IllegalArgumentException("Moedas não podem ser negativas.");
        }

        this.name = name;
        this.coins = coins;
        this.level = 1;
        this.energy = 100;

        this.inventory = new Inventory();
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

    public void receiveReward(Reward r) {
        if (r.getItemReward() != null) {
            this.inventory.insertItem(r.getItemReward());
        }
        this.coins += r.getCoin();
    }

    public void completeQuest(Quest quest) {
        if (quest == null) {
            throw new IllegalArgumentException("Mission Invalid.");
        }

        Reward reward = quest.completeQuest(this.inventory);

        if (reward != null) {
            this.receiveReward(reward);
        }
    }
}

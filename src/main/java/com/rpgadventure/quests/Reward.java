package quests;

import equipment.Item;

public final class Reward {
    private final String description;
    private final int coin;
    private final Item itemReward;

    public Reward(String description, int coin, Item itemReward) {
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("quests.Reward description cannot be empty or null.");
        }
        if (coin < 0) {
            throw new IllegalArgumentException("Coin value cannot be negative.");
        }

        this.description = description;
        this.coin = coin;
        this.itemReward = itemReward;
    }

    public int getCoin() {
        return coin;
    }

    public Item getItemReward() {
        return itemReward;
    }

    public String getDescription() {
        return description;
    }
}

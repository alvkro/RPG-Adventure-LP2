package entities;

import java.util.HashSet;
import java.util.Set;

import equipment.*;
import quests.*;

public class Adventurer extends Entity {
    private String name;
    private int energy;
    private int level;
    private int coins;
    private Inventory<Item> inventory;
    private Set<Quest> activeQuests;

    public Adventurer(String name, int coins) {
        super(name, coins);
        this.activeQuests = new HashSet<>();
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

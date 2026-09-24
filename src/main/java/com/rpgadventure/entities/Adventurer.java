package entities;

import java.util.HashSet;
import java.util.Set;

import equipment.Item;
import equipment.ItemNotFoundException;
import quests.*;

public class Adventurer extends Entity {
    private Set<Quest> activeQuests;

    public Adventurer(String name, int coins) {
        super(name, coins);
        this.activeQuests = new HashSet<>();
    }

    public void dialogue() {
        System.out.println("Talking...");
    }

    public void acceptQuest(Quest quest) {
        if (quest == null) {
            throw new IllegalArgumentException("Quest cannot be null.");
        }

        boolean wasAdded = this.activeQuests.add(quest);

        if (wasAdded) {
            System.out.println(">> New mission accepted: " + quest.getTitle());
        } else {
            System.out.println(">> You already have this mission active!");
        }
    }

    public void receiveReward(Reward r) {
        if (r.getItemReward() != null) {
            this.getInventory().insertItem(r.getItemReward());
        }
        this.addCoins(r.getCoin());
    }

    public void completeQuest(Quest quest) {
        if (quest == null) {
            throw new IllegalArgumentException("Mission Invalid.");
        }

        if (!this.activeQuests.contains(quest)) {
            System.out.println(">> You are not participating in this mission!");
            return;
        }

        Reward reward = quest.completeQuest(this.getInventory());

        if (reward != null) {
            this.receiveReward(reward);
            this.activeQuests.remove(quest);
            System.out.println(">> Mission completed successfully!");
        } else {
            System.out.println(">> You still don't have the requirements to complete this mission.");
        }
    }

    public void showActiveQuests() {
        System.out.println("\n--- Active Missions ---");
        if (this.activeQuests.isEmpty()) {
            System.out.println("No missions at the moment.");
        } else {
            for (Quest q : this.activeQuests) {
                System.out.println("- " + q.getTitle());
            }
        }
        System.out.println("----------------------\n");
    }

    public void useItem(String itemName) throws ItemNotFoundException {
        Item itemToUse = null;

        for (Item item : this.getInventory().getItemsKeys()) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                itemToUse = item;
                break;
            }
        }

        if (itemToUse == null) {
            throw new ItemNotFoundException("You don't have an item called '" + itemName + "'!");
        }

        if (itemToUse instanceof equipment.Usable) {
            ((equipment.Usable) itemToUse).use(this);

            this.getInventory().removeItem(itemToUse);

        } else {
            System.out.println("> You cannot use the " + itemToUse.getName() + " like that!");
        }
    }
}

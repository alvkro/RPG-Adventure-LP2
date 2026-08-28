public class Quest {
    private final String title;
    private final String description;
    private final Reward reward;
    private final Item requiredItem;
    private QuestState questState = QuestState.AVAILABLE;

    public Quest(String title, String description, Reward reward, Item required_item) {
        if (title == null || title.isBlank())
            throw new IllegalArgumentException("Title cannot be null or empty.");

        this.title = title;
        this.description = description;
        this.reward = reward;
        this.requiredItem = required_item;
    }

    public void obtainQuest() {
        if (this.questState != QuestState.AVAILABLE)
            throw new IllegalStateException("You cannot obtain this quest right now.");

        this.questState = QuestState.IN_PROGRESS;
    }

    public Reward completeQuest(Inventory inventory) {
        if (this.questState != QuestState.IN_PROGRESS)
            throw new IllegalStateException("You cannot complete a mission you have not obtained.");

        if (!inventory.hasItem(requiredItem))
            throw new IllegalStateException("Required item not yet obtained.");

        this.questState = QuestState.COMPLETED;
        inventory.removeItem(requiredItem);

        return reward;
    }

    public void failQuest() {
        if (this.questState != QuestState.IN_PROGRESS)
            throw new IllegalStateException("Cannot fail a mission not in progress.");

        this.questState = QuestState.FAILED;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Reward getReward() {
        return reward;
    }

    public Item getRequiredItem() {
        return requiredItem;
    }

    public QuestState getQuestState() {
        return questState;
    }

    @Override
    public String toString() {
        return "Title: " + this.title + "\nDescription: " + this.description
                + "\nReward: " + this.reward + "\nRequired item: " + this.requiredItem
                + "\nState: " + this.questState;
    }
}

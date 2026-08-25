public class Quest {
  private final String title;
  private final String description;
  private final Reward reward;
  private final Item requiredItem;
  private QuestState questState = QuestState.AVAILABLE;

  public Quest(String title, String description, Reward reward, Item required_item) {
    this.title = title;
    this.description = description;
    this.reward = reward;
    this.requiredItem = required_item;
  }

  public void obtainQuest() {
    this.questState = QuestState.IN_PROGRESS;
  }

  public Reward completeQuest() {
    this.questState = QuestState.COMPLETED;

    return reward;
  }

  public void failQuest() {
    this.questState = QuestState.FAILED;
  }

  @Override
  public String toString() {
    return "Title: " + this.title + "\nDescription: " + this.description
        + "\nReward: " + this.reward + "\nRequired item: " + this.requiredItem
        + "\nState: " + this.questState;
  }
}

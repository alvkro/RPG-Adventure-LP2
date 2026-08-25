public class Quest {
  private final String title;
  private final String description;
  private final Reward reward;
  private QuestState quest_state = QuestState.AVAILABLE;

  public Quest(String title, String description, Reward reward) {
    this.title = title;
    this.description = description;
    this.reward = reward;
  }

  public void obtainQuest() {
    this.quest_state = QuestState.IN_PROGRESS;
  }

  public Reward completeQuest() {
    this.quest_state = QuestState.COMPLETED;

    return reward;
  }

  @Override
  public String toString() {
    return "Title: " + this.title + "\nDescription: " + this.description
        + "\nReward: " + this.reward + "\nState: " + this.quest_state;
  }
}

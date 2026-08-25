public class Missao {
  private final String title;
  private final Recompensa reward;
  private boolean is_done = false;

  public Missao(String title, Recompensa reward) {
    this.title = title;
    this.reward = reward;
  }

  public Recompensa doMission() {
    is_done = true;

    return reward;
  }

  @Override
  public String toString() {
    return "Título: " + this.title + "\nRecompensa: " + reward +
        "\nConcluída" + (is_done ? "Sim" : "Não");
  }
}
